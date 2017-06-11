package top.zhacker.testdriven.templateengine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by zhacker.
 * Time 2017/6/4 上午10:07
 * Desc 文件描述
 */
public class TestTemplate {
    
    private Template template;
    
    @Before
    public void setUp(){
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }
    
//    @Test
//    public void oneVariable(){
//        Template template = new Template("Hello, ${name}");
//        template.set("name", "Reader");
//        assertEquals("Hello, Reader", template.evaluate());
//
//    }
//
//    @Test
//    public void differentValue(){
//        Template template = new Template("Hello, ${name}");
//        template.set("name", "Writer");
//        assertEquals("Hello, Writer", template.evaluate());
//    }
//
//    @Test
//    public void differentTemplate(){
//        Template template = new Template("Hi, ${name}");
//        template.set("name", "Reader");
//        assertEquals("Hi, Reader", template.evaluate());
//    }
    
    @Test
    public void multipleVariables(){
        assertTemplateEvaluatesTo("1, 2, 3");
    }
    
    @Test
    public void unknownVariablesAreIgnored(){
        template.set("doesNotUsed", "Hi");
        assertTemplateEvaluatesTo("1, 2, 3");
    }
    
    @Test
    public void missingValueRaisesException(){
        try{
            new Template("${foo}").evaluate();
            fail("evaluate() should throw an exception if a variable missing a value");
        }catch (MissingValueException e){
            assertEquals("No value for ${foo}", e.getMessage());
        }
    }
    
    @Test
    public void variablesGetProcessedJustOnce(){
        template.set("one", "${one}");
        template.set("two", "${two}");
        template.set("three", "${three}");
        assertTemplateEvaluatesTo("${one}, ${two}, ${three}");
    }
    
    
    private void assertTemplateEvaluatesTo(String expected){
        assertEquals(expected, template.evaluate());
    }
}
