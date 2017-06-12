# test driven

## service test with PowerMockito

### setup PowerMockito
```java
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
        //Whitebox.setInternalState(productService, "productRepo", productRepo); //当为private时
    }   
}
```
* @PrepareForTest: 包含静态方法的类、待测试的类等
* 待测试类要spy，而不是用mock。 另外，@InjectMocks不支持mock自身方法。当内部字段为private时，采用WhiteBox来设置内部状态
* 异常验证可以用@Test(expected=Xxx.class), 但要深入时，用@Rule ExpectedException。

### mock or suppress

* mock's public: 
```java
when(productRepo.save(product)).thenReturn(product);
```

* spy's public: 
```java
doReturn(new Product()).when(productService).findByName("p1");
```

* spy's private: 
```java
doReturn(new Product()).when(productService, "findByNo", "n1");
```

* spy's private void: 
```java
doNothing().when(productService, "checkCategoryId", 0L);
```

* spy's private void: 
```java
doThrow(new RuntimeException("category.invalid")).when(productService, "checkCategoryId", 0L);
```


* suppress static constructor: 
```java
@SuppressStaticInitializationFor("top.zhacker.testdriven.product.model.Product")
```

* suppress instance constructor: 
```java
suppress(constructor(Product.class));
```

* suppress method invoke: 
```java
suppress(method(Product.class,"check", Product.class));
```

* mock static method: 
```java
mockStatic(ProductGenerator.class); when(ProductGenerator.getNextId()).thenReturn("no");
```

* mock static method void: 
```java
mockStatic(Product.class); doNothing().when(Product.class, "check", any());
```

* mock static method void: 
```java
mockStatic(Product.class); doThrow(new RuntimeException("check.fail")).when(Product.class, "check", any());
```

* mock constructor: 
```java
whenNew(Product.class).withArguments("invalid").thenReturn(mock(Product.class));
```

* mock different returns
```java
    when(productRepo.findByName(any())).then(invocationOnMock->{
        String name = invocationOnMock.getArgument(0);
        if(name.startsWith("pa")) return new Product("pa");
        if(name.startsWith("pb")) return new Product("pb");
        return null;
    });
```
### verify

* exception: 
```java
exception.expect(RuntimeException.class);
exception.expectMessage("check.fail");
```

* params: 
```java
    verify(productRepo).save((Product)argThat(p->{ //overload
        Product toSave = (Product)p; //overload
        assertEquals("pa", toSave.getName());
        assertNotNull(toSave.getCreatedAt());
        assertNotNull(toSave.getNo());
        assertNotNull(toSave.getUrl());
        return true;
    }));
   
```
* verify private: 
```java
verifyPrivate(productService).invoke("updateProduct", product);
```

* verify static: 
```java
verifyStatic(times(1)); Product.check(product); // invoke static verify, important
```