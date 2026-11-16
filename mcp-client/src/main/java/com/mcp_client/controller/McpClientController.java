package com.mcp_client.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class McpClientController {

    private final ChatClient chatClient;

    public McpClientController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(toolCallbackProvider)
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
