package com.sergiotejeri.servicioprecios.aplicacion.servicio;

/** Indica que no hay tarifas vigentes para los criterios solicitados. */
public class PrecioNoEncontradoException extends RuntimeException {

  public PrecioNoEncontradoException() {
    super("No existe un precio aplicable para los datos solicitados.");
  }
}
