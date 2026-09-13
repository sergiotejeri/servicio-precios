package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada.ConsultarPrecioAplicable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/precios")
public class ControladorPrecios {

	private final ConsultarPrecioAplicable consultarPrecioAplicable;

	public ControladorPrecios(ConsultarPrecioAplicable consultarPrecioAplicable) {
		this.consultarPrecioAplicable = consultarPrecioAplicable;
	}

	@GetMapping
	public RespuestaPrecio consultar(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaAplicacion,
			@RequestParam int idProducto,
			@RequestParam int idMarca) {
		var precio = consultarPrecioAplicable.consultar(fechaAplicacion, idProducto, idMarca);
		return new RespuestaPrecio(
				precio.idProducto(), precio.idMarca(), precio.idTarifa(), precio.fechaInicio(),
				precio.fechaFin(), precio.importe(), precio.moneda());
	}
}
