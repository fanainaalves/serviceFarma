package com.edu.serviceFarma.service;

import com.edu.serviceFarma.dto.ProductCreatedDTO;
import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;
import com.edu.serviceFarma.repositories.ProductRepository;
import com.edu.serviceFarma.utils.ErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Page<ProductDTO> findAllProductByTypeAndSearch(String type, String search, Pageable pageable) {
        return productRepository.findByTypeAndTitleContainingOrTypeAndCodeContaining(
                        ProductType.valueOf(type.toUpperCase()),
                        search,
                        ProductType.valueOf(type.toUpperCase()),
                        search,
                        pageable)
                .map(ProductDTO::new);
    }

    public Page<ProductDTO> findAllProductBySearch(String search, Pageable pageable) {
        return productRepository.findByTitleContainingOrCodeContaining(search, search, pageable)
                .map(ProductDTO::new);
    }

    public Page<ProductDTO> findAllProduct(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductDTO::new);
    }

    public Page<ProductDTO> findByType(ProductType type, Pageable pageable){
        return productRepository.findByType(type, pageable).map(ProductDTO::new);
    }


    public ProductDTO findProductByID(Long id){
        Product product = productRepository.findById(id).get();
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }

    public ResponseEntity<Object> saveProduct(ProductDTO productDTO){
        if (productDTO.getTitle() == null || productDTO.getTitle().isBlank()){
            ErrorResponse errorResponse = new ErrorResponse("Título obrigatório");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (productDTO.getCode() == null || isCodeAlreadyExists(productDTO.getCode())){
            ErrorResponse errorResponse = new ErrorResponse("Código já existe ou é inválido");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (productDTO.getType() == ProductType.UNIDADE ||
                productDTO.getType() == ProductType.CAIXA ||
                productDTO.getType() == ProductType.CARTELA && productDTO.getAmount() == null){
            productDTO.setAmount(1);
        }
        Product product = new Product(productDTO.getId(), productDTO.getTitle(), productDTO.getType(), productDTO.getAmount(), productDTO.getCode());
        product = productRepository.save(product);
        ProductCreatedDTO productCreatedDTO = new ProductCreatedDTO(product.getId());
        return ResponseEntity.ok().body(productCreatedDTO);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product entity = productRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        entity.setTitle(productDTO.getTitle());
        entity.setType(productDTO.getType());
        entity.setAmount(productDTO.getAmount());
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
        return productRepository.existsByCode(code);
    }

    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("token", token);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonNode.toString(), headers);

        try {
            restTemplate.exchange("http://localhost:8080/auth/api/v1/authorization/validation", HttpMethod.POST, requestEntity, Void.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

}
