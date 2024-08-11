package com.edu.serviceFarma.controller;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.ProductType;
import com.edu.serviceFarma.service.ProductService;
import com.edu.serviceFarma.utils.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findAllProduct(
            HttpServletRequest request,
            Pageable pageable,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search){
        String authorizationHeader = request.getHeader("Authorization");

        if(!productService.validateToken(authorizationHeader)){
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        try {
            Page<ProductDTO> productPage = productService.findAllProduct(pageable);
            if(type != null && search != null){
                productPage = productService.findAllProductByTypeAndSearch(type, search, pageable);
            } else if (type != null) {
                productPage = productService.findByType(ProductType.valueOf(type), pageable);
            } else if (search != null) {
                productPage = productService.findAllProductBySearch(search, pageable);
            } else {
                productPage = productService.findAllProduct(pageable);
            }

            if (productPage.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/product/")
    @ResponseBody
    public ResponseEntity<Object> findByType(@RequestParam ProductType type, HttpServletRequest request, Pageable pageable){
        String authorizationHeader = request.getHeader("Authorization");

        if(!productService.validateToken(authorizationHeader)){
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        try {
            Page<ProductDTO> productPage = productService.findByType(type, pageable);
            if (productPage.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro ao buscar produtos por tipo"));
        }
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
