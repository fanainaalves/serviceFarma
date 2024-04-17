package com.edu.serviceFarma.controller;

import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.ProductType;
import com.edu.serviceFarma.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            // Validação dos campos obrigatórios
            if (productDTO.getTitle() == null || productDTO.getType() == null) {
                return ResponseEntity.badRequest().body(productDTO);
            }

            // Validação do campo "code" único
            if (productService.isCodeAlreadyExists(productDTO.getCode())) {
                return ResponseEntity.badRequest().body(productDTO);
            }

            // Se o tipo for UNIDADE, o campo "amount" não é obrigatório
            if (productDTO.getType() == ProductType.UNIDADE && productDTO.getAmount() == null) {
                productDTO.setAmount(1); // Defina um valor padrão
            }

            // Salva o produto no banco de dados
            ProductDTO savedProduct = productService.saveProduct(productDTO);

            return ResponseEntity.ok().body(productService.saveProduct(productDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Outros métodos do controller (por exemplo, listar produtos, buscar por ID, etc.)

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
