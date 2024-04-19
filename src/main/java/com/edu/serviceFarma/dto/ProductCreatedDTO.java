package com.edu.serviceFarma.dto;

public class ProductCreatedDTO {

    private Integer id;

    public ProductCreatedDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
