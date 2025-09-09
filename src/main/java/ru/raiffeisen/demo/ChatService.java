package ru.raiffeisen.demo;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.raiffeisen.demo.Model.Chat;

import java.util.List;

public class ChatService {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatService myProxy;

    public List<Chat> getAllChats(){

    }

    public Chat findChat(long chatid){
        return chatRepo
    }

    public Chat createNewChat(){
        Chat.builder().build();
        chatRepo.save();
    }

    public void delete(long chatId){
        chatRepo.deletebyid();
    }

    public void proceedInteraction(Long chatid, String promt) {
        myProxy.addChatEntry(chatid,promt, USER);
        String answer = chatClient.prompt().user(promt).call().content();
        myProxy.addChatEntry(chatid, answer, ASSISTANT);
    }

    @Transactional
    public void addChatEntry(long chatID, String prompt, Role role){
        Chat chat = chatRepo.findByChatId(chatId).orElseThrow();
        chat.addEntry(prompt, role);
    }


    public SseEmitter proceedInteractionWithStreaming(Long chatid, String promt) {
        //myProxy.addChatEntry(chatid,promt, Role.USER);
        SseEmitter emitter = new SseEmitter(0L);
        //StringBuilder answer = new StringBuilder();

        chatClient.prompt()
                .advisors(advisorSpec -> {advisorSpec.param(ChatMemory.CONVERSATION_ID, chatid);})
                .user(promt).stream().chatResponse().subscribe(
                    response -> processToken(response, emitter),
                    emitter::completeWithError,
                    ()->{
                        //myProxy.addChatEntry(chatid,answer.toString(),Role.ASSISTANT);
                    }
                );

        /*

        тут можно сделать эдвайзера и пероедать чатID
                chatClient.prompt()
                .adviser(MessageChatMemoryAdvisor.builder().conversationId(chatid))
                .user(promt).stream().chatResponse().subscribe(
                response -> processToken(response, emitter),
                emitter::completeWithError,
                ()->{
                    //myProxy.addChatEntry(chatid,answer.toString(),Role.ASSISTANT);
                }
        );
         */


        //chatClient.prompt();
        return emitter;
    }


    @SneakyThrows
    private void processToken(String response, SseEmitter emitter, StringBuilder answer){
        emitter.send(response.getResult().getOutput());
        //answer.append(response.getResult().getOutput().getText());
    }
}
