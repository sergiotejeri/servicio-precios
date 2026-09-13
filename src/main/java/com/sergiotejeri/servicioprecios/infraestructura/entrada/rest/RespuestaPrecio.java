package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RespuestaPrecio(
		int idProducto,
		int idMarca,
		int idTarifa,
		LocalDateTime fechaInicio,
		LocalDateTime fechaFin,
		BigDecimal importe,
		String moneda) {
}
