package top.zhacker.testdriven.templateengine;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.val;


/**
 * Created by zhacker.
 * Time 2017/6/4 上午10:13
 * Desc 文件描述
 */
public class Template {
    
    private Map<String, String> variables;
    
    private String templateText;
    
    public Template(String templateText) {
        this.templateText = templateText;
        variables = new HashMap<>();
    }
    
    public void set(String variable, String value) {
        this.variables.put(variable, value);
    }
    public String evaluate(){
        return new TemplateParse().parseSegments(templateText)
                .stream()
                .map(this::replaceVariableWithValue)
                .collect(Collectors.joining());
    }
    
    private String replaceVariableWithValue(Segment segment){
        return segment.evaluate(variables);
    }
    
    
//    public String evaluate(){
//        String result = new TemplateParse().parse(templateText)
//                .stream()
//                .map(this::replaceVariableWithValue)
//                .collect(Collectors.joining());
//        return result;
//    }
    
//    private String replaceVariableWithValue(String segment){
//        if(isVariable(segment)){
//            String variable = segment.substring(2, segment.length() -1);
//            if(!variables.containsKey(variable)){
//                throw new MissingValueException("No value for "+ segment);
//            }
//            return variables.get(variable);
//        }else{
//            return segment;
//        }
//    }
//
//
//    private boolean isVariable(String segment) {
//        return segment.startsWith("${") && segment.endsWith("}");
//    }
    
    //    public String evaluate() {
//        String result = replaceVariables();
//        checkForMissingValues(result);
//        return result;
//    }
    
    
//    private String replaceVariables() {
//        String result = templateText;
//        for(val entry : variables.entrySet()){
//            String regex = "\\$\\{" + entry.getKey() + "}" ;
//            result = result.replaceAll(regex, entry.getValue());
//        }
//        return result;
//    }
//
//
//    private void checkForMissingValues(String result) {
//        Matcher m = Pattern.compile("\\$\\{.+}").matcher(result);
//        if(m.find()){
//            throw new MissingValueException("No value for " + m.group());
//        }
//    }
}
