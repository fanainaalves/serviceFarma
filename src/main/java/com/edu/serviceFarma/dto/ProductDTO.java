package com.edu.serviceFarma.dto;

import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;

import java.util.Objects;

public class ProductDTO {

    private Integer id;
    private String title;
    private ProductType type;
    private Integer amount;
    private String code;

    public ProductDTO(){
        super();
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.type = product.getType();
        this.amount = product.getAmount();
        this.code = product.getCode();
    }

    public ProductDTO(Integer id, String title, ProductType type, Integer amount, String code) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && type == that.type && Objects.equals(amount, that.amount) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, amount, code);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", code='" + code + '\'' +
                '}';
    }
}
