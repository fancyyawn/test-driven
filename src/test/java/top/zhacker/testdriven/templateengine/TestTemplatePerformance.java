package top.zhacker.testdriven.templateengine;

import com.google.common.base.Stopwatch;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertTrue;


/**
 * Created by zhacker.
 * Time 2017/6/4 上午11:17
 * Desc 文件描述
 */
@Slf4j
public class TestTemplatePerformance {
    
    private Template template;
    
    @Before
    public void setup(){
        String templateText = IntStream.range(1, 21).mapToObj(i -> "${v"+i + "} ").collect(Collectors.joining());
        template = new Template(templateText);
        IntStream.range(1,21).forEach(i->{
            template.set("v"+i, String.valueOf(i));
        });
    }
    
    @Test
    public void templateWith100WordsAnd20Variables(){
        long expectedMs = 1000L;
        Stopwatch stopwatch = Stopwatch.createStarted();
        template.evaluate();
        stopwatch.stop();
        long time = stopwatch.elapsed(TimeUnit.MICROSECONDS);
        log.info("{}", time);
        assertTrue("Rendering the template took " + time + "ms", time <= expectedMs);
    }
}
