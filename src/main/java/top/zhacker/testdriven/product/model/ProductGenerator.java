package top.zhacker.testdriven.product.model;

import java.util.UUID;


/**
 * Created by zhacker.
 * Time 2017/5/28 上午10:27
 * Desc 文件描述
 */
public final class ProductGenerator {
    
    public static String getNextId(){
        return UUID.randomUUID().toString();
    }
}
