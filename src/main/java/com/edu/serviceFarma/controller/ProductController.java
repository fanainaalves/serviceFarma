package com.edu.serviceFarma.controller;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;
import com.edu.serviceFarma.service.ProductService;
import com.edu.serviceFarma.utils.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (!productService.validateToken(authorizationHeader)) {
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        return productService.saveProduct(productDTO);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAllProduct(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if(!productService.validateToken(authorizationHeader)){
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        try{
            List<ProductDTO> productDTOList = productService.findAllProduct();
            if (productDTOList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productDTOList);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/products/")
    @ResponseBody
    public ResponseEntity<Object> findProductByType(@RequestParam ProductType type, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if(!productService.validateToken(authorizationHeader)){
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        List<Product> products = productService.findProductsByType(type);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/<id>/{id}")
    public ResponseEntity<Object> findProductByID(@PathVariable Long id, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if(!productService.validateToken(authorizationHeader)){
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

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
