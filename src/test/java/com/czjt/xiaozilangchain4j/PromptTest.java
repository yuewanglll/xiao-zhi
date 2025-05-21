package com.czjt.xiaozilangchain4j;

import com.czjt.xiaozilangchain4j.assistant.MemoryChatAssistant;
import com.czjt.xiaozilangchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;


    @Test
    public void testPrompt() {
        String answer = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer);
    }


    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chat(1, "今天几号");
        System.out.println(answer);
    }

    @Test
    public void testV() {
        String answer1 = separateChatAssistant.chat2(3, "我是小智");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat2(3, "我是谁");
        System.out.println(answer2);
    }

    @Test
    public void testUserInfo() {
        String answer = separateChatAssistant.chat3(4, "我是谁，我多大了", "小智", 18);
        System.out.println(answer);
    }


    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Test
    public void testUserMessage() {
        String answer = memoryChatAssistant.chat("我是小智");
        System.out.println(answer);
    }


}
