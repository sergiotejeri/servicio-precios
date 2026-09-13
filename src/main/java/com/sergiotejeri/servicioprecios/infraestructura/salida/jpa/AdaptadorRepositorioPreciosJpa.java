package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdaptadorRepositorioPreciosJpa implements RepositorioPrecios {

	private final RepositorioPreciosJpa repositorioPreciosJpa;

	public AdaptadorRepositorioPreciosJpa(RepositorioPreciosJpa repositorioPreciosJpa) {
		this.repositorioPreciosJpa = repositorioPreciosJpa;
	}

	@Override
	public List<Precio> buscarAplicables(LocalDateTime fechaAplicacion, int idProducto, int idMarca) {
		return repositorioPreciosJpa
				.findByIdProductoAndIdMarcaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPrioridadDesc(
						idProducto, idMarca, fechaAplicacion, fechaAplicacion)
				.stream()
				.map(this::aDominio)
				.toList();
	}

	private Precio aDominio(PrecioJpa precio) {
		return new Precio(
				precio.id(),
				precio.idMarca(),
				precio.idTarifa(),
				precio.idProducto(),
				precio.fechaInicio(),
				precio.fechaFin(),
				precio.prioridad(),
				precio.importe(),
				precio.moneda());
	}
}
