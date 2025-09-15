package ru.raiffeisen;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.raiffeisen.model.Chat;
import ru.raiffeisen.model.ChatEntry;
import lombok.Builder;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

@Service
public class PostgresChatMemory implements ChatMemory {
    @Setter
    @Getter
    private ChatRepo chatMemoryRepository;

    @Setter
    @Getter
    private int maxMessages;


    @Override
    public void add(String conversationId, List<Message> messages) {
        Chat chat = this.chatMemoryRepository.findById(Long.valueOf(13)).orElseThrow();
        messages.stream()
                .map(ChatEntry::fromMessage)
                .forEach(chat::addEntry);

        chatMemoryRepository.save(chat);

    }

    @Override
    public List<Message> get(String conversationId) {
        Chat chat = this.chatMemoryRepository.findById(Long.valueOf(13)).orElseThrow();
        int messagesToSkip = Math.max(0, chat.getHistory().size() - maxMessages);
        return chat.getHistory().stream()
                .map(ChatEntry::toMessage)
                .skip(messagesToSkip)
                .toList();
    }

    @Override
    public void clear(String conversationId) {

    }
}