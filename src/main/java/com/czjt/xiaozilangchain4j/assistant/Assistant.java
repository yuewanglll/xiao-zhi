package com.czjt.xiaozilangchain4j.assistant;

import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;




@AiService(wiringMode = EXPLICIT,
        chatModel = "qwenChatModel")  //模型的beanName
public interface Assistant {
    String chat(String userMessage);
}

