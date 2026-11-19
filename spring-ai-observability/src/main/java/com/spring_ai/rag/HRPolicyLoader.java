package com.spring_ai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HRPolicyLoader {
    private final VectorStore vectorStore;

    @Value("classpath:Eazybytes_HR_Policies.pdf")
    private Resource policyFile;

    public HRPolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPDF() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
        List<Document> docs = tikaDocumentReader.get();
        // split the text every 100 tokens, the doc can't have more than 400 tokens
        TokenTextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
        vectorStore.add(textSplitter.split(docs));

    }
}
