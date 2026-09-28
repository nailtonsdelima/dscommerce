package com.nailton.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		// return result.stream().map(x -> new ProductDTO(x)).toList();
		return result.map(x -> new ProductDTO(x));
	}

}
