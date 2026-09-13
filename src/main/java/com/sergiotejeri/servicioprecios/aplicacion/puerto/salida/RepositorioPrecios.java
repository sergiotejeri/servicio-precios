package com.sergiotejeri.servicioprecios.aplicacion.puerto.salida;

import com.sergiotejeri.servicioprecios.dominio.Precio;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioPrecios {

	List<Precio> buscarAplicables(LocalDateTime fechaAplicacion, int idProducto, int idMarca);
}
