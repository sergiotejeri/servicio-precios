package com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada;

import com.sergiotejeri.servicioprecios.dominio.Precio;
import java.time.LocalDateTime;

/** Consulta el precio vigente de mayor prioridad para un producto y una marca. */
public interface ConsultarPrecioAplicable {

  Precio consultar(LocalDateTime fechaAplicacion, int idProducto, int idMarca);
}
