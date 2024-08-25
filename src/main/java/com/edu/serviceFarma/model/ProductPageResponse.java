package com.edu.serviceFarma.model;

import com.edu.serviceFarma.dto.ProductDTO;

import java.util.List;

public class ProductPageResponse {
    private List<ProductDTO> items;
    private long total;

    public ProductPageResponse(List<ProductDTO> items, long total) {
        this.items = items;
        this.total = total;
    }

    public List<ProductDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductDTO> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
