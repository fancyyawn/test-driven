package top.zhacker.testdriven.product.model;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2017/5/27 下午1:18
 * Desc 文件描述
 */
@Entity
@Data
@Accessors(chain = true)
public class Product implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    private String no;
    private Long categoryId;
    private String name;
    private String detail;
    private String url;
    private Date createdAt;
    
    public static String DEFAULT_URL;
    
    static {
        DEFAULT_URL = "staticConstructor:" + UUID.randomUUID().toString();
    }
    
    public Product(){
        createdAt = new Date();
    }
    
    public Product(String name){
        if(name.contains("invalid")){
            throw new RuntimeException("invalid.product.name");
        }
        this.name = name;
    }
    
    public static void check(Product product){
        if(Strings.isNullOrEmpty(product.getDetail())){
            throw new RuntimeException("detail.require");
        }
    }
}
