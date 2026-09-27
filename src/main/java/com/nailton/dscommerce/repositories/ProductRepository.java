package com.nailton.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nailton.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
