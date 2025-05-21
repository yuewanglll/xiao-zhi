package com.czjt.xiaozilangchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {
    @Tool(name = "加法",value = "返回两个指的数相加之和")
    double sum(@ToolMemoryId int memoryId, @P(value = "加数1") double a, @P(value = "加数2") double b) {
        System.out.println("调用加法运算 memoryId"+memoryId);
        return a + b;
    }

    @Tool
    double squareRoot(@ToolMemoryId int memoryId,double x) {
        System.out.println("调用平方根运算 memoryId"+memoryId);
        return Math.sqrt(x);
    }
}
