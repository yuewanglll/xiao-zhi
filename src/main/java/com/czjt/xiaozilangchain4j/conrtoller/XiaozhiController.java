package com.czjt.xiaozilangchain4j.conrtoller;



import com.czjt.xiaozilangchain4j.assistant.XiaozhiAgent;
import com.czjt.xiaozilangchain4j.pojo.ChatFrom;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "小智智能体")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {

    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @PostMapping("/test")
    public String testXiaozhi(@RequestBody ChatFrom chatFrom){
        String chat = xiaozhiAgent.chat(chatFrom.getMemoryId(), chatFrom.getUserMessage());
        return chat;
    }

}
