package top.zhacker.testdriven.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhacker.testdriven.product.model.Product;
import top.zhacker.testdriven.product.service.ProductService;


/**
 * Created by zhacker.
 * Time 2017/6/11 下午10:24
 * Desc 文件描述
 */
@RestController
@RequestMapping("/v1/products")
public class Products {
    
    private final ProductService productService;
    
    @Autowired
    public Products(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findById(id);
    }
    
    @PostMapping("")
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }
}
