package top.zhacker.testdriven.templateengine;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午8:19
 * Desc 文件描述
 */
public class TestPlainTextSegment {
    
    @Test
    public void test_evaluate() throws Exception{
        String text = "abc";
        
        String evaluated = new PlainText(text).evaluate(new HashMap<>());
        
        assertEquals(text, evaluated);
    }
}
