package top.zhacker.testdriven.templateengine;

/**
 * Created by zhacker.
 * Time 2017/6/4 上午10:59
 * Desc 文件描述
 */
public class MissingValueException extends RuntimeException {
    
    public MissingValueException(String message) {
        super(message);
    }
}
