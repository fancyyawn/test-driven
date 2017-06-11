package top.zhacker.testdriven.templateengine;

import java.util.Map;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午6:14
 * Desc 文件描述
 */
public interface Segment {
    
    String evaluate(Map<String, String> context);
    
    
}
