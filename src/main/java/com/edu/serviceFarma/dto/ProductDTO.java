package com.edu.serviceFarma.dto;

import com.edu.serviceFarma.model.ProductType;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private ProductType type;
    private Integer amount;
    private String code;

    public ProductDTO(){
        super();
    }
}
