package com.curso.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Calzado;

@Repository
public interface IProductoRepository extends JpaRepository<Calzado, Integer> {

}
