package com.crud.tienda.controllers;

import com.crud.tienda.dtos.Mensajes;
import com.crud.tienda.dtos.ProductoDto;
import com.crud.tienda.entities.ProductoEntity;
import com.crud.tienda.services.ProductoService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @PostMapping("/crearProducto")
    public ResponseEntity<?> agregarProducto(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensajes("Nombre obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio() == null || productoDto.getPrecio() <= 0)
            return new ResponseEntity(new Mensajes("Precio obligatorio o valor mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existePorNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensajes("Nombre del producto ya existe"), HttpStatus.BAD_REQUEST);
        ProductoEntity productoEntity = new ProductoEntity(productoDto.getNombre(), productoDto.getPrecio());
        productoService.crear(productoEntity);
        return new ResponseEntity(new Mensajes("Producto guardado"), HttpStatus.OK);
    }

    @GetMapping("/listarProductos")
    public ResponseEntity<List<ProductoEntity>> listar(){
        List<ProductoEntity> list = productoService.listar();
        return new ResponseEntity<List<ProductoEntity>>(list, HttpStatus.OK);
    }

    @PutMapping("/actualizarProducto/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable("id") int id, @RequestBody ProductoDto productoDto){
        if(!productoService.existePorId(id))
            return new ResponseEntity(new Mensajes("No existe"), HttpStatus.NOT_FOUND);
        if(productoService.existePorNombre(productoDto.getNombre()) && productoService.listarPorNombre(productoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensajes("Nombre del producto ya existe"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensajes("Nombre obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio() < 0)
            return new ResponseEntity(new Mensajes("Precio obligatorio o valor mayor que 0"), HttpStatus.BAD_REQUEST);

        ProductoEntity productoEntity = productoService.listarPorId(id).get();
        productoEntity.setNombre(productoDto.getNombre());
        productoEntity.setPrecio(productoDto.getPrecio());
        productoService.crear(productoEntity);
        return new ResponseEntity(new Mensajes("Producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") int id){
        if(!productoService.existePorId(id))
            return new ResponseEntity(new Mensajes("No existe"), HttpStatus.NOT_FOUND);
        productoService.eliminar(id);
        return new ResponseEntity(new Mensajes("Producto eliminado"), HttpStatus.OK);
    }

    @GetMapping("/detallesProductoId/{id}")
    public ResponseEntity<ProductoEntity> listarPorId(@PathVariable("id") int id){
        if(!productoService.existePorId(id))
            return new ResponseEntity(new Mensajes("Producto no disponible"), HttpStatus.NOT_FOUND);
        ProductoEntity productoEntity = productoService.listarPorId(id).get();
            return new ResponseEntity<ProductoEntity>(productoEntity, HttpStatus.OK);
    }

    @GetMapping("/detallesProductoNombre/{nombre}")
    public ResponseEntity<ProductoEntity> listarPorNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existePorNombre(nombre))
            return new ResponseEntity(new Mensajes("Producto no disponible"), HttpStatus.NOT_FOUND);
        ProductoEntity productoEntity = productoService.listarPorNombre(nombre).get();
            return new ResponseEntity<ProductoEntity>(productoEntity, HttpStatus.OK);
    }

}
