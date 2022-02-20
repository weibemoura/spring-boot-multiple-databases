package br.com.weibe.spring.boot.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.weibe.spring.boot.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
