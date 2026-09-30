package com.sergiotejeri.servicioprecios.aplicacion.servicio;

public class PrecioNoEncontradoException extends RuntimeException {

    public PrecioNoEncontradoException() {
        super("No existe un precio aplicable para los datos solicitados.");
    }
}
