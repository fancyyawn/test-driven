package top.zhacker.testdriven.xp.chapter6;

/**
 * Created by zhacker.
 * Time 2017/5/29 下午7:39
 * Desc 文件描述
 */
public class Frame {
    
    private int score = 0;
    
    public void add(int pins) {
        score += pins;
    }
    
    
    public int getScore() {
        return score;
    }
}
