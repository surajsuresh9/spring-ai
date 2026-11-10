package com.spring_ai.config;

import com.spring_ai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {

        OllamaChatOptions.Builder chatOptions = OllamaChatOptions
                .builder()
                .temperature(0.9)
                .maxTokens(500);

        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help\\s
                        employees with questions related to HR policies, such as\\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\\s
                        kindly inform them that you can only assist with queries related to\\s
                        HR policies.
                        """)
                .defaultUser("How can you help me?")
                .defaultOptions(chatOptions)
                .build();
    }
}
