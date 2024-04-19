package com.edu.serviceFarma.controller;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.service.ProductService;
import com.edu.serviceFarma.utils.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (!productService.validateToken(authorizationHeader)) {
            ErrorResponse errorResponse = new ErrorResponse("Token inválido");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        return productService.saveProduct(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProduct(){
        return ResponseEntity.ok(productService.findAllProduct());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductByID(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO entity){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, entity));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
