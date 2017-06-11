package top.zhacker.testdriven.xp.chapter6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by zhacker.
 * Time 2017/5/29 下午7:41
 * Desc 文件描述
 */
public class TestFrame {
    
    @Test
    public void test_score_noThrows(){
        Frame frame = new Frame();
        assertEquals(0, frame.getScore());
    }
    
    @Test
    public void test_addOneThrow(){
        Frame f = new Frame();
        f.add(5);
        assertEquals(5, f.getScore());
    }
}
