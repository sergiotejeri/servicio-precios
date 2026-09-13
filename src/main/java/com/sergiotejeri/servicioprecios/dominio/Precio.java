package com.sergiotejeri.servicioprecios.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
}
