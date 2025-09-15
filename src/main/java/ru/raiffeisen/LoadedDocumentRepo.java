package ru.raiffeisen;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.raiffeisen.model.LoadedDocument;

public interface LoadedDocumentRepo extends JpaRepository<LoadedDocument, Long> {
    boolean existsByFilenameAndContentHash(String filename, String s);
}
