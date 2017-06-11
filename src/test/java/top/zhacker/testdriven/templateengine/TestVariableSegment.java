package top.zhacker.testdriven.templateengine;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午8:21
 * Desc 文件描述
 */
public class TestVariableSegment {
    
    @Test
    public void test_evaluate() throws Exception{
        Map<String, String> context = new HashMap<>();
        String name = "name";
        String value = "value";
        context.put(name, value);
        
        String evaluated = new Variable(name).evaluate(context);
        
        assertEquals(value, evaluated);
    }
    
    @Test
    public void test_missingVariableRaisesException() throws Exception{
        try{
            new Variable("name").evaluate(new HashMap<>());
            fail("should throw exception when variable missing value");
        }catch (MissingValueException e){
            assertEquals("No value for ${name}", e.getMessage());
        }
    }
}
