package ru.raiffeisen.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoadedDocument {

    @Entity
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor

    public class LoadedDocument(){
        @Id
        @GeneratedValue
        public int id;

    }
}
