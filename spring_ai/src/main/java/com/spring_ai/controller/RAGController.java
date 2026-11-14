package com.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RAGController {

    @Value("classpath:promptTemplates/systemPromptRandomDataTemplate.st")
    private Resource promptTemplate;

    @Value("classpath:promptTemplates/systemPromptTemplate.st")
    private Resource hrSystemTemplate;

    private final ChatClient chatClient;
    private final ChatClient webSearchChatClient;
    private final VectorStore vectorStore;

    public RAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
                         @Qualifier("webSearchRAGChatClient")
                         ChatClient webSearchChatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.webSearchChatClient = webSearchChatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestHeader("username") String username, @RequestParam("message") String message) {
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(message)
//                .topK(3)
//                .similarityThreshold(0.5)
//                .build();
//
//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient
                .prompt()
//                .system(promptSystemSpec ->
//                        (promptSystemSpec.text(promptTemplate)).param("documents", similarContext))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call().content();
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/document/chat")
    public ResponseEntity<String> documentChat(@RequestHeader("username") String username, @RequestParam("message") String message) {
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(message)
//                .topK(2)
//                .similarityThreshold(0.5)
//                .build();

//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient
                .prompt()
//                .system(promptSystemSpec ->
//                        (promptSystemSpec.text(hrSystemTemplate)).param("documents", similarContext))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call().content();
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearchChat(
            @RequestHeader("username") String username,
            @RequestParam("message") String message) {

        String answer = webSearchChatClient.prompt()
                .options(OllamaChatOptions.builder()
                        .model("gemma4:e2b")
                        .numPredict(2048))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        if (answer == null || answer.isBlank()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LLM returned an empty response");
        }

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
