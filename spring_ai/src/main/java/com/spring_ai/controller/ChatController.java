
package com.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.ollama.api.OllamaChatOptions;


@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam("message") String message) {

        return chatClient.prompt()
//                .user(message)// override behavior
                .system("""
                        You are an internal IT helpdesk assistant. Your role is to assist employees 
                        with IT-related issues such as resetting passwords, unlocking accounts, and 
                        answering questions related to IT policies. 
                        If a user request help outside of these responsibilities, respond politely and 
                        inform them that you are only able to assist with IT support tasks within
                        your defined scope 
                        """)
                .user(message)
                .call()
                .content();
    }
}
