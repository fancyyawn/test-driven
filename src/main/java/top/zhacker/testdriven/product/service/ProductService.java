package top.zhacker.testdriven.product.service;

import top.zhacker.testdriven.product.model.Product;


/**
 * Created by zhacker.
 * Time 2017/5/27 下午1:29
 * Desc 文件描述
 */
public interface ProductService {
    
    Product saveProduct(Product product);
    
    Product findById(Long id);
}
