package com.spring_ai.config;

import com.spring_ai.advisors.TokenUsageAuditAdvisor;
import com.spring_ai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:promptTemplates/helpDeskSystemPromptTemplate.st")
    private Resource systemPromptTemplate;

    @Bean("helpdeskChatClient")
    public ChatClient helpdeskChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, TimeTools timeTools) {
//        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
//        TokenUsageAuditAdvisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
        return chatClientBuilder
                .defaultSystem(systemPromptTemplate)
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(messageChatMemoryAdvisor))
                .build();
    }

    @Bean
    public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }
}
