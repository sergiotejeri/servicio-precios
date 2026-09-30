package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RepositorioPreciosJpa extends JpaRepository<PrecioJpa, Long> {

    Optional<PrecioJpa> findFirstByIdProductoAndIdMarcaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPrioridadDesc(
            int idProducto,
            int idMarca,
            LocalDateTime fechaAplicacionParaInicio,
            LocalDateTime fechaAplicacionParaFin);
}
