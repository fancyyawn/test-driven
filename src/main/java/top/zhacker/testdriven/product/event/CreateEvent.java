package top.zhacker.testdriven.product.event;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.testdriven.product.model.Product;


/**
 * Created by zhacker.
 * Time 2017/5/28 上午10:47
 * Desc 文件描述
 */
@Data
@Accessors(chain = true)
public class CreateEvent {
    
    private Product product;
    
    private Date createdAt = new Date();
    
}
