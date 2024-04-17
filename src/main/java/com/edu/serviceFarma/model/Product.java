package com.edu.serviceFarma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Título do produto
    private ProductType type; // Tipo do produto (CAIXA, UNIDADE, CARTELA)
    private Integer amount; // Quantidade (opcional se o tipo for UNIDADE)
    private String code; // Código único

    public Product(){
        super();
    }
}
