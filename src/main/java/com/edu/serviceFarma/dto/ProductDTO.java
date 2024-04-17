package com.edu.serviceFarma.dto;

import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;
import lombok.*;

@Data
@AllArgsConstructor

public class ProductDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private ProductType type;

    @Getter
    @Setter
    private Integer amount;

    @Getter
    @Setter
    private String code;

    public ProductDTO(){
        super();
    }

    public ProductDTO(Product product) {
    }
}
