package com.edu.serviceFarma.model;

import com.edu.serviceFarma.dto.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

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

    public Product(Long id, String title, ProductType type, Integer amount, String code) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.code = code;
    }

    public Product(ProductDTO productDTO) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(title, product.title) && type == product.type && Objects.equals(amount, product.amount) && Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, amount, code);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", code='" + code + '\'' +
                '}';
    }
}
