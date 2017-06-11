package top.zhacker.testdriven.product.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import top.zhacker.testdriven.product.model.Product;


/**
 * Created by zhacker.
 * Time 2017/5/27 下午1:28
 * Desc 文件描述
 */
@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Long> {
    
    Product findByName(String name);
    
    Product findByNo(String no);
}
