package ru.raiffeisen.demo.Model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.raiffeisen.demo.Role;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder

public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany
    @JoinColumn(name="chat.id")
    @OrderBy("createdAt ASC")
    private List<ChatEntry> history;

    public void addEntry(String prompt, Role role){
        history.add(ChatEntry.builder().content(prompt).role(role).builder);
    }

    public void addEntry(ChatEntry chatEntry) {

    }
}
