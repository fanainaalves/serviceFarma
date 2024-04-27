package com.edu.serviceFarma.repositories;

import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByCode(String code);
    boolean existsByCode(String code);
    Page<Product> findAll(Pageable pageable); // Adicionando método para busca pageada
    List<Product> findByType(ProductType type);
}
