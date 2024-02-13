package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    Product findById(int id);

    List<Product> findByPrixGreaterThan(int prixLimit);

  /*  @Query("SELECT id, nom, prix FROM Product p WHERE p.prix > :prixLimit")
    List<Product>  chercherUnProduitCher(@Param("prixLimit") int prix);*/
}