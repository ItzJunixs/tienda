package com.crud.tienda.dtos;

import lombok.NonNull;

public class ProductoDto {
    @NonNull
    private String nombre;
    private Float precio;

    public ProductoDto() {
    }

    public ProductoDto(@NonNull String nombre, Float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}
