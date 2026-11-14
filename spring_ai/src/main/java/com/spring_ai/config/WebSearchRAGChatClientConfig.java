package com.spring_ai.config;

import com.spring_ai.advisors.TokenUsageAuditAdvisor;
import com.spring_ai.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRAGChatClientConfig {

    @Bean("webSearchRAGChatClient")
    public ChatClient chatMemory(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, RestClient.Builder restClient) {
        // Advisors
        Advisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder().documentRetriever(WebSearchDocumentRetriever
                        .builder()
                        .restClientBuilder(restClient).maxResults(5).build())
                .build();
        return chatClientBuilder
                .defaultAdvisors(List.of(simpleLoggerAdvisor, tokenUsageAuditAdvisor, messageChatMemoryAdvisor, retrievalAugmentationAdvisor))
                .build();
    }
}