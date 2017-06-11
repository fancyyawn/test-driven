package top.zhacker.testdriven.product.service;

import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import top.zhacker.testdriven.product.dao.ProductRepo;
import top.zhacker.testdriven.product.event.CreateEvent;
import top.zhacker.testdriven.product.model.Product;
import top.zhacker.testdriven.product.model.ProductGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.constructor;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;


/**
 * Created by zhacker.
 * Time 2017/5/28 下午7:28
 * Desc 文件描述
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProductServiceImpl.class, ProductGenerator.class, Product.class})
public class ProductServiceImplTest {
    
    @Mock
    private ProductRepo productRepo;
    
    @Mock
    private EventBus eventBus;
    
    @Rule
    private ExpectedException exception = ExpectedException.none();
    
    private ProductServiceImpl productService;
    
    @Before
    public void init(){
        productService = spy(new ProductServiceImpl(productRepo, eventBus));
    }
    
    @Test
    public void test_saveProduct_create_nameDuplicate1() throws Exception {
    
        /**
         * ProductServiceImpl.findByName impact the logic of saveProduct
         */
        exception.expectMessage("not.exist");
        productService.saveProduct(new Product().setName("p1"));
    }
        
    @Test
    public void test_saveProduct_create_nameDuplicate() throws Exception {
        
        /**
         * mock spy's public method with returns, skip impact of findByName
         */
        //when(productService.findByName("p1")).thenReturn(new Product());  //won't work
        doReturn(new Product()).when(productService).findByName("p1");
    
        exception.expect(RuntimeException.class);
        exception.expectMessage("name.duplicate");
        
        productService.saveProduct(new Product().setName("p1"));
    }
    
    @Test
    public void test_saveProduct_create_noDuplicate() throws Exception{
    
        exception.expectMessage("not.exist");
        productService.saveProduct(new Product().setNo("n1"));
    
        /**
         * mock spy's private method with returns, skip impact of findByNo
         */
        doReturn(new Product()).when(productService, "findByNo", "n1");
        
        exception.expect(RuntimeException.class);
        exception.expectMessage("no.duplicate");
        
        productService.saveProduct(new Product().setNo("n1"));
        
    }
    
    @Test
    public void test_saveProduct_create_checkCategory() throws Exception{
    
        exception.expectMessage("category.id.not.zero");
        productService.saveProduct(new Product().setName("p2").setDetail("detail").setCategoryId(0L));
        
        /**
         * mock spy's private method without returns, skip impact of checkCategoryId
         */
        doNothing().when(productService, "checkCategoryId", 0L);
        productService.saveProduct(new Product().setName("p2").setDetail("detail").setCategoryId(0L));
    }
    
    @Test
    public void test_checkCategory() throws Exception {
        doThrow(new RuntimeException("category.invalid")).when(productService, "checkCategoryId", 0L);
        
        exception.expectMessage("category.invalid");
        productService.saveProduct(new Product().setName("p2").setDetail("detail").setCategoryId(0L));
    }
    
    
    @Test
    public void test_saveProduct_create_generateNextId() throws Exception {
        Product product = new Product().setName("product-a").setDetail("detail");
    
        /**
         * because mock return null by default, so you can skip it
         */
        //doReturn(null).when(productService, "findByName", "product-a");
    
        /**
         * mock static method with returns
         */
        mockStatic(ProductGenerator.class);
        when(ProductGenerator.getNextId()).thenReturn("no");
        when(productRepo.save(product)).thenReturn(product);
    
        Product result = productService.saveProduct(product);
        
        assertEquals("no", result.getNo());
    }
    
    @Test
    public void test_product_defaultUrl(){
        assertTrue(Product.DEFAULT_URL.startsWith("staticConstructor"));
    }
    
    @Test
    public void test_saveProduct_create_setUrl() throws Exception {
        Product product = new Product().setName("product-a").setDetail("detail");
        when(productRepo.save(product)).thenReturn(product);
        
        Product result = productService.saveProduct(product);
        
        assertTrue(result.getUrl().startsWith("staticConstructor"));
    }
    
    
    /**
     * suppress static constructor
     */
    @RunWith(PowerMockRunner.class)
    @PrepareForTest({Product.class})
    @SuppressStaticInitializationFor("top.zhacker.testdriven.product.model.Product")
    public static class ProductTest{
        
        @Test
        public void test_product_defaultUrl_suppress_static_constructor(){
            assertNull(Product.DEFAULT_URL);
        }
        
        @Test
        public void test_product_createAt_suppress_constructor(){
            /**
             * suppress instance constructor
             */
            suppress(constructor(Product.class));
            assertNull(new Product().getCreatedAt());
        }
    }
    
    @Test
    public void test_saveProduct_create_check() {
    
        exception.expect(RuntimeException.class);
        exception.expectMessage("detail.require");
    
        productService.saveProduct(new Product().setName("product-a"));
    }
        
        @Test
    public void test_saveProduct_create_suppress_check(){
        
        /**
         * suppress method
         */
        suppress(method(Product.class,"check", Product.class));
        
        productService.saveProduct(new Product().setName("product-a"));
    }
    
    @Test
    public void test_saveProduct_create_mock_check() throws Exception {
        
        /**
         * mock static method without returns, do nothing
         */
        mockStatic(Product.class);
        doNothing().when(Product.class, "check", any());
    
        Product product = new Product().setName("product-a");
        productService.saveProduct(product);
        
        verifyStatic(times(1));
        Product.check(product); // invoke static verify, important
    }
    
    @Test
    public void test_product_check() throws Exception{
        
        mockStatic(Product.class);
    
        Product product = new Product().setDetail("detail");
        Product.check(product);
        Product.check(product);
        verifyStatic(times(2));
        Product.check(product);
    }
    
    @Test
    public void test_saveProduct_create_mock_check_throws() throws Exception {
        
        /**
         * mock static method without returns, throw exception
         */
        mockStatic(Product.class);
        doThrow(new RuntimeException("check.fail")).when(Product.class, "check", any());
        
        exception.expectMessage("check.fail");
        
        productService.saveProduct(new Product().setName("product-a"));
    }
    
    
    @Test
    public void test_saveProduct_create_doCallRepoSave(){
        val product = new Product().setName("pa").setDetail("detail");
        
        Product result = productService.saveProduct(product);
        
        verify(productRepo).save(eq(product));
    
        /**
         * use verify to trace inner calls
         * care of overload
         */
        verify(productRepo).save((Product)argThat(p->{ //overload
            Product toSave = (Product)p; //overload
            assertEquals("pa", toSave.getName());
            assertNotNull(toSave.getCreatedAt());
            assertNotNull(toSave.getNo());
            assertNotNull(toSave.getUrl());
            return true;
        }));
    }
    
    @SuppressWarnings("ConstantConditions")
    @Test
    public void test_saveProduct_create_doPostEvent(){
        Product product = new Product().setName("pa").setDetail("detail");
        when(productRepo.save(eq(product))).thenReturn(product);
        
        productService.saveProduct(product);
    
        /**
         * use verify to trace inner calls
         */
        verify(eventBus).post(argThat(e->{
            CreateEvent event = (CreateEvent) e;
            assertEquals("pa", event.getProduct().getName());
            return true;
        }));
    }
    
    @Test
    public void test_saveProduct_update() throws Exception {
        
        doNothing().when(productService, "updateProduct", any());
        //suppress(method(ProductServiceImpl.class, "updateProduct", Product.class));
    
        Product product = new Product().setId(1L).setDetail("haha");
        productService.saveProduct(product);
    
        /**
         * verify private invoke
         */
        verifyPrivate(productService).invoke("updateProduct", product);
        verify(productRepo).findOne(argThat(x->{
            assertEquals(1L, x.longValue());
            return true;
        }));
        verify(eventBus).post(any());
        
    }
    
    @Test
    public void test_product_new(){
        
        exception.expectMessage("invalid.product.name");
        
        new Product("invalid");
    }
    
    @Test
    public void test_product_new_mock() throws Exception {
    
        /**
         * mock constructor
         */
        whenNew(Product.class).withArguments("invalid").thenReturn(mock(Product.class));
        
        new Product("invalid");
        new Product("correct");
        
        verifyNew(Product.class).withArguments("invalid");
        verifyNew(Product.class).withArguments("correct");
    }
    
    @Test
    public void test_product() throws Exception {
        Product product = new Product().setName("pa").setDetail("detail");
        String name = Whitebox.getInternalState(product, "name");
        assertEquals("pa", name);
        
        Whitebox.setInternalState(product, "name", "pb");
        assertEquals("pb", product.getName());
        
        Whitebox.invokeMethod(product, "setName", "pc");
        assertEquals("pc", product.getName());
    }
    
    @Test
    public void test_findByName(){
        when(productRepo.findByName(any())).then(invocationOnMock->{
            String name = invocationOnMock.getArgument(0);
            if(name.startsWith("pa")) return new Product("pa");
            if(name.startsWith("pb")) return new Product("pb");
            return null;
        });
        
        assertEquals("pa", productService.findByName("pa").getName());
        assertEquals("pb", productService.findByName("pb").getName());
        assertNull(productService.findByName("any"));
    }
    
    @Test
    public void test_findByNo_noExist() throws Exception {
        
        exception.expectMessage("not.exist");
        Whitebox.invokeMethod(productService, "findByNo", "n1");
    }
    
    @Test
    public void test_findByNo_exist() throws Exception {
        
        when(productRepo.findByNo(any())).thenReturn(new Product().setName("p2"));
        
        //test private method
        Product product = Whitebox.invokeMethod(productService, "findByNo", "n2");
        
        assertNotNull(product);
        assertEquals("p2", product.getName());
    }
}
