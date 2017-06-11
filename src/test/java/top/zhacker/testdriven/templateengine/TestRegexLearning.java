package top.zhacker.testdriven.templateengine;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午4:45
 * Desc 文件描述
 */
public class TestRegexLearning {
    
    @Test
    public void testHowGroupCountWorks(){
        String text = "The needle shop sells needles";
        String regex = "(needle)";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        assertEquals(2, matcher.groupCount());
    }
    
    @Test
    public void testFindStartAndEnd(){
        String text = "The needle shop sells needles";
        String regex = "needle";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        
        assertTrue(matcher.find());
        assertEquals(4, matcher.start());
        assertEquals(10, matcher.end());
        
        assertTrue(matcher.find());
        assertEquals(22, matcher.start());
        assertEquals(28, matcher.end());
        
        assertFalse("should not have any more matches", matcher.find());
    }
    
    
}
