package com.nailton.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nailton.dscommerce.dto.ProductDTO;
import com.nailton.dscommerce.entities.Product;
import com.nailton.dscommerce.repositories.ProductRepository;
import com.nailton.dscommerce.services.exceptions.DatabaseException;
import com.nailton.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Propagation;
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
		Product product = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Item não encontrado") );
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		// return result.stream().map(x -> new ProductDTO(x)).toList();
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);		
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {			
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);		
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resurso não encontrado");
		}
	}
	
	// Antigo sem disparar exception
	//	@Transactional
	//	public void delete(Long id) {
	//		repository.deleteById(id);
	//	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
	        repository.deleteById(id);
		}
	    catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Falha de integridade referencial");
	   	}
	}
	
	public void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
	}

}
