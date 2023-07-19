package com.crud.tienda.repositories;

import com.crud.tienda.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
    Optional<ProductoEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
