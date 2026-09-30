package com.sergiotejeri.servicioprecios.aplicacion.servicio;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada.ConsultarPrecioAplicable;
import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;

import java.time.LocalDateTime;

public class ConsultarPrecioAplicableService implements ConsultarPrecioAplicable {

    private final RepositorioPrecios repositorioPrecios;

    public ConsultarPrecioAplicableService(RepositorioPrecios repositorioPrecios) {
        this.repositorioPrecios = repositorioPrecios;
    }

    @Override
    public Precio consultar(LocalDateTime fechaAplicacion, int idProducto, int idMarca) {
        return repositorioPrecios.buscarPrecioAplicable(fechaAplicacion, idProducto, idMarca)
                .orElseThrow(PrecioNoEncontradoException::new);
    }
}
