package com.leandro.market.domain.repository;

import com.leandro.market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {//estas son las reglas del dominio
    //esto hace que no nos acomplemos a una bbdd especifica
    //sino que siempre estemos hablando en términos de dominio
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarseProducts(int quantity);
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    void delete(int productId);
}
