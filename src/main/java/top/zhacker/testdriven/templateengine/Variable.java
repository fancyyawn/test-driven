package top.zhacker.testdriven.templateengine;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午6:17
 * Desc 文件描述
 */
@ToString
@EqualsAndHashCode
public class Variable implements Segment {
    
    @Getter
    private String variable;
    
    public Variable(String variable){
        this.variable = variable;
    }
    
    @Override
    public String evaluate(Map<String, String> context) {
        if(context.containsKey(variable)){
            return context.get(variable);
        }else{
            throw new MissingValueException("No value for ${" + variable + "}");
        }
    }
}
