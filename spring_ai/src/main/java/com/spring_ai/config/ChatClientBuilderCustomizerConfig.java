package com.spring_ai.config;

import com.spring_ai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBuilderCustomizerConfig {

    @Bean
    public ChatClientBuilderCustomizer loggerCustomizer() {
        return builder -> builder.defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean
    // control customizations
    @ConditionalOnProperty(name = "audit.token-usage.enabled", havingValue = "true")
    public ChatClientBuilderCustomizer auditAdvisor() {
        return builder -> builder.defaultAdvisors(new TokenUsageAuditAdvisor());
    }
}
