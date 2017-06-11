package top.zhacker.testdriven.templateengine;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午6:16
 * Desc 文件描述
 */
@ToString
@EqualsAndHashCode
public class PlainText implements Segment{
    
    @Getter
    private String text;
    
    public PlainText(String text){
        this.text = text;
    }
    
    @Override
    public String evaluate(Map<String, String> context) {
        return text;
    }
}
