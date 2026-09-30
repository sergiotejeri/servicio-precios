package com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada;

import com.sergiotejeri.servicioprecios.dominio.Precio;

import java.time.LocalDateTime;

public interface ConsultarPrecioAplicable {

    Precio consultar(LocalDateTime fechaAplicacion, int idProducto, int idMarca);
}
