package com.sergiotejeri.servicioprecios.aplicacion.servicio;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada.ConsultarPrecioAplicable;
import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ConsultarPrecioAplicableService implements ConsultarPrecioAplicable {

	private final RepositorioPrecios repositorioPrecios;

	public ConsultarPrecioAplicableService(RepositorioPrecios repositorioPrecios) {
		this.repositorioPrecios = repositorioPrecios;
	}

	@Override
	public Precio consultar(LocalDateTime fechaAplicacion, int idProducto, int idMarca) {
		return repositorioPrecios.buscarAplicables(fechaAplicacion, idProducto, idMarca).stream()
				.max(Comparator.comparingInt(Precio::prioridad))
				.orElseThrow();
	}
}
