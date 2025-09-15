package ru.raiffeisen;


import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raiffeisen.model.Chat;

import java.util.List;

@Repository
@Primary
public interface ChatRepo extends JpaRepository<Chat, Long> {
}
