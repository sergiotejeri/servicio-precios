package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Ejecuta en la base de datos el filtrado y la selección de la tarifa de mayor prioridad. */
public interface RepositorioPreciosJpa extends JpaRepository<PrecioJpa, Long> {

  @Query(
      value =
          """
          SELECT * FROM PRICES
          WHERE PRODUCT_ID = :idProducto AND BRAND_ID = :idMarca
            AND START_DATE <= :fechaAplicacion AND END_DATE >= :fechaAplicacion
          ORDER BY PRIORITY DESC
          FETCH FIRST 1 ROW ONLY
          """,
      nativeQuery = true)
  Optional<PrecioJpa> buscarPrecioAplicable(
      int idProducto, int idMarca, LocalDateTime fechaAplicacion);
}
