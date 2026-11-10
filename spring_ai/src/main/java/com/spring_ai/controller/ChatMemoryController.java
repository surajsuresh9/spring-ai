package com.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestHeader("username") String username, @RequestParam("message") String message) {
        return new ResponseEntity<>(chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call().content(), HttpStatus.OK);
    }
}
