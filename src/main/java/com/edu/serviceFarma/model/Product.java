package com.edu.serviceFarma.model;

import com.edu.serviceFarma.dto.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String title; // Título do produto

    @Getter
    @Setter
    private ProductType type; // Tipo do produto (CAIXA, UNIDADE, CARTELA)

    @Getter
    @Setter
    private Integer amount; // Quantidade (opcional se o tipo for UNIDADE)

    @Getter
    @Setter
    private String code; // Código único

    public Product(){
        super();
    }

    public Product(ProductDTO productDTO) {
    }
}
