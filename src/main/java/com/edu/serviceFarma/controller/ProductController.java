package com.edu.serviceFarma.controller;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.ProductPageResponse;
import com.edu.serviceFarma.service.ProductService;
import com.edu.serviceFarma.utils.ErrorResponse;
import com.edu.serviceFarma.model.ProductType;
import com.edu.serviceFarma.model.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "http://localhost:4200")
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
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search) {

        if (page < 1) {
            page = 1;
        }

        Pageable paging = PageRequest.of(page - 1, size);

        String authorizationHeader = request.getHeader("Authorization");

        if (!productService.validateToken(authorizationHeader)) {
            ErrorResponse errorResponse = new ErrorResponse("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Page<ProductDTO> productPage;
        if (search != null && !search.isEmpty()) {
            if (type != null) {
                productPage = productService.findAllProductByTypeAndSearch(type, search, paging);
            } else {
                productPage = productService.findAllProductBySearch(search, paging);
            }
        } else if (type != null) {
            productPage = productService.findByType(ProductType.valueOf(type), paging);
        } else {
            productPage = productService.findAllProduct(paging);
        }

        if (productPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ProductPageResponse(productPage.getContent(), productPage.getTotalElements()));
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
