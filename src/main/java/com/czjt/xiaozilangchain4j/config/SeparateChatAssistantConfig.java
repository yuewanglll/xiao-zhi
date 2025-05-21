package com.czjt.xiaozilangchain4j.config;


import com.czjt.xiaozilangchain4j.strore.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeparateChatAssistantConfig {
    //注入持久化对象
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoChatMemoryStore)  //持久化对象
                .build();

//        return new ChatMemoryProvider() {
//            @Override
//            public ChatMemory get(Object o) {
//                return MessageWindowChatMemory.builder()
//                        .id(o)
//                        .maxMessages(10)
//                        .build();
//            }
//        };
    }
}
