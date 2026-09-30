package com.sergiotejeri.servicioprecios.aplicacion.puerto.salida;

import com.sergiotejeri.servicioprecios.dominio.Precio;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RepositorioPrecios {

    Optional<Precio> buscarPrecioAplicable(LocalDateTime fechaAplicacion, int idProducto, int idMarca);
}
