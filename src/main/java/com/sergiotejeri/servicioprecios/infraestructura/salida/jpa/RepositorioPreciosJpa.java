package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioPreciosJpa extends JpaRepository<PrecioJpa, Long> {

	List<PrecioJpa> findByIdProductoAndIdMarcaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPrioridadDesc(
			int idProducto,
			int idMarca,
			LocalDateTime fechaAplicacionParaInicio,
			LocalDateTime fechaAplicacionParaFin);
}
