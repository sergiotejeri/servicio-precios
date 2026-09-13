package com.sergiotejeri.servicioprecios.aplicacion.servicio;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsultarPrecioAplicableServiceTest {

	@Test
	void seleccionaElPrecioConMayorPrioridadEntreLosAplicables() {
		var fechaAplicacion = LocalDateTime.of(2020, 6, 14, 16, 0);
		var precioPrioridadBaja = new Precio(
				1L, 1, 1, 35455,
				LocalDateTime.of(2020, 6, 14, 0, 0),
				LocalDateTime.of(2020, 12, 31, 23, 59, 59),
				0, new BigDecimal("35.50"), "EUR");
		var precioPrioridadAlta = new Precio(
				2L, 1, 2, 35455,
				LocalDateTime.of(2020, 6, 14, 15, 0),
				LocalDateTime.of(2020, 6, 14, 18, 30),
				1, new BigDecimal("25.45"), "EUR");

		RepositorioPrecios repositorio = (fecha, idProducto, idMarca) ->
				List.of(precioPrioridadBaja, precioPrioridadAlta);
		var servicio = new ConsultarPrecioAplicableService(repositorio);

		var precioAplicable = servicio.consultar(fechaAplicacion, 35455, 1);

		assertEquals(2, precioAplicable.idTarifa());
		assertEquals(new BigDecimal("25.45"), precioAplicable.importe());
	}
}
