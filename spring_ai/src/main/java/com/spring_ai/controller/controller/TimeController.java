package com.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class TimeController {

    private final ChatClient chatClient;

    public TimeController(@Qualifier("timeChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/local-time")
    public ResponseEntity<String> localTime(@RequestHeader("username") String username, @RequestParam("message") String message) {
        String answer = chatClient
                .prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call().content();
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
