package com.czjt.xiaozilangchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider",  //指定bean的名称
        tools = "calculatorTools"  //指定计算工具bean的名称
)
public interface SeparateChatAssistant {
    /**
     * 分离聊天记录
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     * @return
     */
    @SystemMessage(fromResource = "my-prompt-template.txt")
    //系统提示词，只会发送一次
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
    String chat2(@MemoryId int memoryId, @V("message") String userMessage);

    @SystemMessage(fromResource = "my-prompt-template2.txt")
    String chat3(
            @MemoryId int memoryId,
            @UserMessage String userMessage,
            @V("username") String username,
            @V("age") int age
    );

}
