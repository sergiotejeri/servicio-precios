package com.sergiotejeri.servicioprecios.aplicacion.puerto.salida;

import com.sergiotejeri.servicioprecios.dominio.Precio;
import java.time.LocalDateTime;
import java.util.Optional;

/** Obtiene un único precio aplicable; devuelve vacío si ninguna tarifa está vigente. */
public interface RepositorioPrecios {

  Optional<Precio> buscarPrecioAplicable(
      LocalDateTime fechaAplicacion, int idProducto, int idMarca);
}
