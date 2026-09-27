package com.nailton.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nailton.dscommerce.dto.ProductDTO;
import com.nailton.dscommerce.entities.Product;
import com.nailton.dscommerce.repositories.ProductRepository;

//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
	//		Optional<Product> result = repository.findById(id);
	//		Product product = result.get();
	//		ProductDTO dto = new ProductDTO(product);
	//		return dto;
		
		Product product = repository.findById(id).get();
		return new ProductDTO(product);
	}

}
