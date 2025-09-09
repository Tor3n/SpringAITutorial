package ru.raiffeisen.demo;

import lombok.Builder;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import ru.raiffeisen.demo.Model.Chat;
import ru.raiffeisen.demo.Model.ChatEntry;

import java.util.List;

@Builder
public class PostgresChatMemory implements ChatMemory {

    private ChatRepo chatMemoryRepository;
    private int maxMessages;

    @Override
    public void add(String conversationId, Message message) {

    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        Chat chat = chatMemoryRepository.findById(Long.valueOf(conversationId)).orElseThrow();
        messages.stream()
                .map(ChatEntry::fromMessage)
                .forEach(chat::addEntry);
        chatMemoryRepository.save(chat);
    }

    @Override
    public List<Message> get(String conversationId) {
        Chat chat = chatMemoryRepository.findById(Long.valueOf(conversationId)).orElseThrow();
        int messagesToSkip = Math.max(0, chat.getHistory().size() - maxMessages);
        return chat.getHistory().stream().map(ChatEntry::toMessage)
                 .skip(messagesToSkip).toList();
    }

    @Override
    public void clear(String conversationId) {

    }

    public static PostgresChatMemory.Builder builder() {
        return new PostgresChatMemory.Builder();
    }

    public static final class Builder {
        private ChatMemoryRepository chatMemoryRepository;
        private int maxMessages = 20;

        private Builder() {
        }

        public MessageWindowChatMemory.Builder chatMemoryRepository(ChatMemoryRepository chatMemoryRepository) {
            this.chatMemoryRepository = chatMemoryRepository;
            return this;
        }

        public MessageWindowChatMemory.Builder maxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
            return this;
        }

        public MessageWindowChatMemory build() {
            if (this.chatMemoryRepository == null) {
                this.chatMemoryRepository = new InMemoryChatMemoryRepository();
            }

            return new MessageWindowChatMemory(this.chatMemoryRepository, this.maxMessages);
        }
    }
}
