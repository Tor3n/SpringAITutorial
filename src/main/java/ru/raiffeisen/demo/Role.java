package ru.raiffeisen.demo;

import org.springframework.ai.chat.me

public enum Role {
    USER("user"),
    SYSTEM("system"),
    ASSISTANT("assistant");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public abstract Message toMessage();
}
