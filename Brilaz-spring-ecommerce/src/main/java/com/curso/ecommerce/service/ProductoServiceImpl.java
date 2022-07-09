package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Calzado;
import com.curso.ecommerce.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private IProductoRepository productoRepository;

	@Override
	public Calzado save(Calzado producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Calzado> get(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void update(Calzado producto) {
		productoRepository.save(producto);		
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);		
	}

	@Override
	public List<Calzado> findAll() {
		return productoRepository.findAll();
	}
        @Override
	public Optional<Calzado> findById(Integer id) {
		return productoRepository.findById(id);
	}

}
