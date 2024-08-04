package com.edu.serviceFarma.repositories;

import com.edu.serviceFarma.model.Product;
import com.edu.serviceFarma.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByCode(String code);
    boolean existsByCode(String code);
    Page<Product> findAll(Pageable pageable); // Adicionando método para busca pageada
    Page<Product> findByType(ProductType type, Pageable pageable);
    Page<Product> findByTitleContainingOrCodeContaining(String title, String code, Pageable pageable);
    Page<Product> findByTypeAndTitleContainingOrTypeAndCodeContaining(ProductType productType1, String title, ProductType productType2, String code, Pageable pageable);
}
