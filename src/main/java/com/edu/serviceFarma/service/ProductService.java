package com.edu.serviceFarma.service;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAllProduct(){
        List<Product> listEntity = productRepository.findAll();
        List<ProductDTO> listDTO = listEntity.stream().map(e -> new ProductDTO(e)).toList();
        return listDTO;
    }

    public ProductDTO findProductByID(Long id){
        Product product = productRepository.findById(id).get();
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }
    public ProductDTO saveProduct(ProductDTO entity){
        Product product = new Product(entity);
        productRepository.save(product);
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product entity = productRepository.findById(id).get();
        entity.setCode(productDTO.getCode());
        productRepository.save(entity);
        ProductDTO newProductDTO = new ProductDTO(entity);
        return newProductDTO;
    }
    public Product deleteProduct(Long id){
        Product entity = productRepository.findById(id).get();
        productRepository.delete(entity);
        return entity;
    }

    public boolean isCodeAlreadyExists(String code) {
        // Verifica se o código já existe no banco de dados
        return productRepository.existsByCode(code);
    }
}
