package ru.raiffeisen.demo.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import  org.springframework.ai.chat.messages.Message;
import ru.raiffeisen.demo.Role;

import java.time.LocalDateTime;

public class ChatEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ChatEntry toMessage(Message message){
        return role.toMessage(content);
    }

    public static ChatEntry fromMessage(Message message){
       return null;
    }

}
