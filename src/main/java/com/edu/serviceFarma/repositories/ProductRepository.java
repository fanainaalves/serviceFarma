package com.edu.serviceFarma.repositories;

import com.edu.serviceFarma.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByCode(String code);
    boolean existsByCode(String code);
//    Ou
//    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.code = :code")
//    boolean existsByCode(@Param("code") String code);
}
