package com.crud.tienda.services;

import com.crud.tienda.entities.ProductoEntity;
import com.crud.tienda.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    public List<ProductoEntity> listar(){
        return productoRepository.findAll();
    }

    public Optional<ProductoEntity> listarPorId(int id){
        return productoRepository.findById(id);
    }

    public Optional<ProductoEntity> listarPorNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void crear(ProductoEntity producto){
        productoRepository.save(producto);
    }

    public void eliminar(int id) {
        productoRepository.deleteById(id);
    }

    public boolean existePorId(int id){
        return productoRepository.existsById(id);
    }

    public boolean existePorNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }
}
