package com.sergiotejeri.servicioprecios.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

/** Tarifa inmutable con importe no negativo, moneda ISO y periodo de vigencia inclusivo. */
public record Precio(
    long id,
    int idMarca,
    int idTarifa,
    int idProducto,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin,
    int prioridad,
    BigDecimal importe,
    String moneda) {

  /** Rechaza identificadores, fechas, importes o monedas que incumplen las reglas del precio. */
  public Precio {
    if (idMarca <= 0 || idTarifa <= 0 || idProducto <= 0) {
      throw new IllegalArgumentException(
          "Los identificadores de un precio deben ser mayores que cero.");
    }
    if (prioridad < 0) {
      throw new IllegalArgumentException("La prioridad de un precio no puede ser negativa.");
    }

    Objects.requireNonNull(fechaInicio, "La fecha de inicio es obligatoria.");
    Objects.requireNonNull(fechaFin, "La fecha de fin es obligatoria.");
    Objects.requireNonNull(importe, "El importe es obligatorio.");
    Objects.requireNonNull(moneda, "La moneda es obligatoria.");

    if (fechaInicio.isAfter(fechaFin)) {
      throw new IllegalArgumentException(
          "La fecha de inicio no puede ser posterior a la fecha de fin.");
    }
    if (importe.signum() < 0) {
      throw new IllegalArgumentException("El importe de un precio no puede ser negativo.");
    }
    try {
      Currency.getInstance(moneda);
    } catch (IllegalArgumentException exception) {
      throw new IllegalArgumentException("La moneda debe usar un código ISO válido.", exception);
    }
  }
}
