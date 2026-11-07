
package com.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.ollama.api.OllamaChatOptions;


@RestController
@RequestMapping("/api")
public class ChatController {

    private ChatClient.Builder chatClientBuilder;
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultSystem("""
                        You are an internal HR assistant. Your role is to help\\s
                        employees with questions related to HR policies, such as\\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\\s
                        kindly inform them that you can only assist with queries related to\\s
                        HR policies.
                        """)
                .defaultUser("How can you help me?")
                .build();
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam("message") String message,
            @RequestParam(defaultValue = "llama3.2") String model) {

        if (!model.equals("llama3.2") &&
                !model.equals("gemma4:e2b")) {
            throw new IllegalArgumentException("Unsupported model");
        }

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
                .options(OllamaChatOptions.builder()
                        .model(model).numPredict(512))
                .call()
                .content();
    }
}
