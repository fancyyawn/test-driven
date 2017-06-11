
## mock or suppress

* mock's public: `when(productRepo.save(product)).thenReturn(product)`;

* spy's public: `doReturn(new Product()).when(productService).findByName("p1");`

* spy's private: `doReturn(new Product()).when(productService, "findByNo", "n1");`

* spy's private void: `doNothing().when(productService, "checkCategoryId", 0L);`

* spy's private void: `doThrow(new RuntimeException("category.invalid")).when(productService, "checkCategoryId", 0L);`

* suppress static constructor: `@SuppressStaticInitializationFor("top.zhacker.testdriven.product.model.Product")`

* suppress instance constructor: `suppress(constructor(Product.class));`

* suppress method invoke: `suppress(method(Product.class,"check", Product.class));`

* mock static method: `mockStatic(ProductGenerator.class); when(ProductGenerator.getNextId()).thenReturn("no");`

* mock static method void: `mockStatic(Product.class); doNothing().when(Product.class, "check", any());`

* mock static method void: `mockStatic(Product.class); doThrow(new RuntimeException("check.fail")).when(Product.class, "check", any());`

* mock constructor: `whenNew(Product.class).withArguments("invalid").thenReturn(mock(Product.class));`

* mock different returns
```java
    when(productRepo.findByName(any())).then(invocationOnMock->{
        String name = invocationOnMock.getArgument(0);
        if(name.startsWith("pa")) return new Product("pa");
        if(name.startsWith("pb")) return new Product("pb");
        return null;
    });
```
## verify

* exception: exception.expectMessage("check.fail");
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
* verify private: `verifyPrivate(productService).invoke("updateProduct", product);`
* verify static: `verifyStatic(times(1)); Product.check(product); // invoke static verify, important`