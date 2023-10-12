package com.multipolar.bootcamp.product.controller;

import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.dto.ErrorMessage;
import com.multipolar.bootcamp.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    membuat product baru
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        Product createdProduct = productService.createOrUpdateProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    //    get semua product
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    //    get product by id lewat pathvariable/segment
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        Optional<Product> product = productService.getProductById((id));
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //    edit product by id
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product){
        product.setId(id);
        return productService.createOrUpdateProduct(product);
    }

    //    delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductId(@PathVariable String id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    //    mengambil products berdasarkan nama productnya
    @GetMapping("/productName/{productName}")
    public List<Product> getProductByProductName(@PathVariable String productName){
        return productService.findByProductName(productName);
    }
}
