package com.parthi.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGPdfDataLoader {

    private final VectorStore vectorStore;

    @Value("classpath:javatechie_employee_handbook_rag_demo.pdf")
    Resource pdfResource;

    public RAGPdfDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPdfDataToVectorStore() {

        // read data from database
        // create one customer support DB (question-answer)

        TikaDocumentReader documentReader = new TikaDocumentReader(pdfResource);
        List<Document> documents = documentReader.get();

        TextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(20)
                .withMaxNumChunks(200)
                .build();

        List<Document> documentChunks = textSplitter.split(documents);
        vectorStore.add(documentChunks);
    }

// 100
// 20 -> 5 chunks
//10 -> 10 chunks - semantic search -
}
