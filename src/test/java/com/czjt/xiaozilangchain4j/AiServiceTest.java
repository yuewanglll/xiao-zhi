package com.czjt.xiaozilangchain4j;


import com.czjt.xiaozilangchain4j.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private QwenChatModel qwenChatModel;
    @Autowired
    private Assistant assistant;

    @Test
    public void test() {
        //创建AiService
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        //调用AiService的接口
        String answer = assistant.chat("你好");
        System.out.println(answer);

    }

    @Test
    public void TestAssistant(){
        String answer = assistant.chat("请问今年7月份java就业形式怎么样？");
        System.out.println(answer);
    }


}
