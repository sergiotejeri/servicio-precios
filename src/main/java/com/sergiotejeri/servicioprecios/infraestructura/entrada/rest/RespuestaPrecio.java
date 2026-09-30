package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Datos de la tarifa seleccionada que recibe el consumidor de la API. */
public record RespuestaPrecio(
    int idProducto,
    int idMarca,
    int idTarifa,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin,
    BigDecimal importe,
    String moneda) {}
