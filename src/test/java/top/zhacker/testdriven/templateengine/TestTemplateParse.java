package top.zhacker.testdriven.templateengine;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午4:58
 * Desc 文件描述
 */
public class TestTemplateParse {
    
    private TemplateParse parse;
    
    @Before
    public void setup(){
        parse = new TemplateParse();
    }
    
    @Test
    public void test_emptyTemplateRendersAsEmptyString() throws Exception{
        List<String> segments = parse.parse("");
        assertSegments(segments, "");
    }
    
    
    @Test
    public void test_templateWithOnlyPlainText() throws Exception{
        List<String> segments = parse.parse("plain text");
        assertSegments(segments, "plain text");
    }
    
    @Test
    public void test_parsingMultipleVariables() throws Exception{
        List<String> segments = parse.parse("${a}:${b}:${c}:nice");
        assertSegments(segments, "${a}", ":", "${b}", ":", "${c}", ":nice");
    }
    
    
    @Test
    public void test_parsingTemplateIntoSegmentObjects() throws Exception{
        List<Segment> segments = parse.parseSegments("a ${b} c ${d} e");
        assertSegments(segments,
                new PlainText("a "),
                new Variable("b"),
                new PlainText(" c "),
                new Variable("d"),
                new PlainText(" e")
        );
    }
    
    /**
     *
     * @param actual
     * @param expected 为数组类型，所以有length
     */
    private void assertSegments(List<? extends Object> actual, Object... expected) {
        assertEquals(expected.length, actual.size());
        assertEquals(Arrays.asList(expected), actual); //compare object, toString() equals
    }
    
}
