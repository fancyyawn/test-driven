package top.zhacker.testdriven.product.service;

import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import top.zhacker.testdriven.product.dao.ProductRepo;
import top.zhacker.testdriven.product.event.CreateEvent;
import top.zhacker.testdriven.product.event.UpdateEvent;
import top.zhacker.testdriven.product.model.Product;
import top.zhacker.testdriven.product.model.ProductGenerator;


/**
 * Created by zhacker.
 * Time 2017/5/27 下午1:30
 * Desc 文件描述
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepo productRepo;
    
    private final EventBus eventBus;
    
    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, EventBus eventBus) {
        this.productRepo = productRepo;
        this.eventBus = eventBus;
    }
    
    @Override
    public Product saveProduct(Product product) {
        if(product.getId()==null) {
            
            //调用了自身Public方法（有返回值）
            Optional.ofNullable(findByName(product.getName())).ifPresent(p -> {
                throw new RuntimeException("name.duplicate");
            });
            
            //调用自身private方法（有返回值）
            Optional.ofNullable(findByNo(product.getNo())).ifPresent(p -> {
                throw new RuntimeException("productno.duplicate");
            });
            
            //调用自身private方法（无返回值）
            checkCategoryId(product.getCategoryId());
            
            //调用了静态方法（有返回值）
            product.setNo(ProductGenerator.getNextId());
            
            //调用了静态变量
            if(product.getUrl()==null){
                product.setUrl(product.DEFAULT_URL);
            }
            
            //调用了静态方法（无返回值）
            Product.check(product);
    
            product = productRepo.save(product);
            
            eventBus.post(new CreateEvent().setProduct(product));
            
            return product;
        }else{
            //调用了自身private方法
            updateProduct(product);
            
            product = productRepo.findOne(product.getId());
            
            eventBus.post(new UpdateEvent().setProduct(product));
            
            return product;
        }
    }
    
    private void updateProduct(Product product){
        productRepo.save(product);
    }
    
    @Override
    public Product findById(Long id) {
        return productRepo.findOne(id);
    }
    
    
    public Product findByName(String name) {
        if(Objects.equals(name, "p1")) {
            throw new RuntimeException("not.exist");
        }
        return productRepo.findByName(name);
    }
    
    private Product findByNo(String no){
        if(Objects.equals(no, "n1")) {
            throw new RuntimeException("not.exist");
        }
        return productRepo.findByNo(no);
    }
    
    private void checkCategoryId(Long categoryId){
        if(Objects.equals(categoryId, 0L)){
            throw new RuntimeException("category.id.not.zero");
        }
    }
}
