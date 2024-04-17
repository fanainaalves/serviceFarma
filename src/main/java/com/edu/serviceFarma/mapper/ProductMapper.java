package com.edu.serviceFarma.mapper;


import com.edu.serviceFarma.dto.ProductDTO;
import com.edu.serviceFarma.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);
}