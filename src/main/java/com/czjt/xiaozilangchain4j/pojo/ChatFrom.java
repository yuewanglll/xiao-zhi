package com.czjt.xiaozilangchain4j.pojo;

import lombok.Data;

@Data
public class ChatFrom {
    private Long memoryId; //对话记录id
    private String userMessage; //用户的问题
}
