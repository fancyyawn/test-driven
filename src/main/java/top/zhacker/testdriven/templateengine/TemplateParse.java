package top.zhacker.testdriven.templateengine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by zhacker.
 * Time 2017/6/5 下午4:58
 * Desc 文件描述
 */
public class TemplateParse {
    
    public static boolean isVariable(String segment){
        return segment.startsWith("${") && segment.endsWith("}");
    }
    
    public List<String> parse(String template) {
        List<String> segments = new ArrayList<>();
        collectSegments(segments, template);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }
    
    public List<Segment> parseSegments(String template){
        return parse(template).stream()
            .map(this::makeSegment)
            .collect(Collectors.toList());
    }
    
    private Segment makeSegment(String segment){
        if(isVariable(segment)){
            return new Variable(segment.substring(2, segment.length()-1));
        }else{
            return new PlainText(segment);
        }
    }
    
    private void collectSegments(List<String> segments, String src) {
        Pattern pattern = Pattern.compile("\\$\\{.+?}"); //? means not greedy
        Matcher matcher = pattern.matcher(src);
        int index = 0;
        while(matcher.find()){
            addPrecedingPlainText(segments, src, matcher, index);
            addVariable(segments, src, matcher);
            index = matcher.end();
        }
        addTail(segments, src, index);
    }
    
    private void addPrecedingPlainText(List<String> segments, String src, Matcher matcher, int index) {
        if(index < matcher.start()){
            segments.add(src.substring(index, matcher.start()));
        }
    }
    
    private void addVariable(List<String> segments, String src, Matcher matcher) {
        segments.add(src.substring(matcher.start(), matcher.end()));
    }
    
    private void addTail(List<String> segments, String src, int index) {
        if(index < src.length()){
            segments.add(src.substring(index));
        }
    }
    
    private void addEmptyStringIfTemplateWasEmpty(List<String> segments) {
        if(segments.isEmpty()){
            segments.add("");
        }
    }
    
}
