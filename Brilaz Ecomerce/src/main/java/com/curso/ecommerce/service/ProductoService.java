package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Calzado;

public interface ProductoService {
	public Calzado save( Calzado producto);
	public Optional<Calzado> get(Integer id);
	public void update(Calzado producto);
	public void delete(Integer id);
	public List<Calzado> findAll();
        Optional<Calzado> findById(Integer id);

}
