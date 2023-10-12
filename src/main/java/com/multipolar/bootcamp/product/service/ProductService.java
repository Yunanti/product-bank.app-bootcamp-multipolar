package com.multipolar.bootcamp.product.service;

import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //    fungsi untuk get all pruduct
    public List<Product> getAllProducts(){ // untuk mendapatkan data dari repository
        return productRepository.findAll();
    }

    //    fungsi untuk get product by id
    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }

    //    fungsi untuk create or update product
    public Product createOrUpdateProduct(Product product){
        return productRepository.save(product);
    }

    //    fungsi untuk menghapus product
    public void deleteProductById(String id){
        productRepository.deleteById(id);
    }

    //   fungsi untuk mencari product berdasarkan nama
    public List<Product> findByProductName(String productName){
        return productRepository.findByProductName(productName);
    }
}
