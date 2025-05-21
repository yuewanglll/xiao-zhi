package com.czjt.xiaozilangchain4j;



import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestLangchain {

    @Autowired
    private OpenAiChatModel model;
    @Autowired
    private OllamaChatModel ollamaModel;
    @Autowired
    private QwenChatModel qwenChatModel;
    // openai大模型测试
    @Test
    void testLangchain() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("你是那个？");
        System.out.println(answer);
    }

    // deepseek大模型测试
    @Test
    void testLangchain2() {
        String answer = model.chat("你是那个？");
        System.out.println(answer);
    }

   //本地大模型测试
    @Test
    void testOllama(){
        String chat = ollamaModel.chat("你是那个？");
        System.out.println(chat);
    }


    @Test
    public void testDashScopeQwen() {
        //向模型提问
        String answer = qwenChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }

    @Test
    public void userMessageAndAiMessage() {
        ChatResponse chat = qwenChatModel.chat(UserMessage.userMessage("你好"));
        AiMessage aiMessage = chat.aiMessage();
        String string = aiMessage.text();
        System.out.println(string);
    }


  //图片生成
    @Test
    public void testDashScopeWanx(){
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASH_SCOPE_API_KEY"))
                .build();
        Response<Image> response = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的\n" +
                "古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她\n" +
                "有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的\n" +
                "连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植\n" +
                "物丛生，营造出神秘而梦幻的氛围");
                System.out.println(response.content().url());
    }

}
