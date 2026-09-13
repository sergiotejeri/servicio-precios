package com.sergiotejeri.servicioprecios.infraestructura.configuracion;

import com.sergiotejeri.servicioprecios.infraestructura.salida.jpa.PrecioJpa;
import com.sergiotejeri.servicioprecios.infraestructura.salida.jpa.RepositorioPreciosJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DatosInicialesH2Configuracion {

	@Bean
	CommandLineRunner cargarPreciosIniciales(RepositorioPreciosJpa repositorioPreciosJpa) {
		return argumentos -> {
			if (repositorioPreciosJpa.count() == 0) {
				repositorioPreciosJpa.saveAll(List.of(
						precio(1, 1, 35455, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 0, "35.50"),
						precio(1, 2, 35455, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 1, "25.45"),
						precio(1, 3, 35455, "2020-06-15T00:00:00", "2020-06-15T11:00:00", 1, "30.50"),
						precio(1, 4, 35455, "2020-06-15T16:00:00", "2020-12-31T23:59:59", 1, "38.95")));
			}
		};
	}

	private PrecioJpa precio(
			int idMarca,
			int idTarifa,
			int idProducto,
			String fechaInicio,
			String fechaFin,
			int prioridad,
			String importe) {
		return new PrecioJpa(
				null,
				idMarca,
				idTarifa,
				idProducto,
				LocalDateTime.parse(fechaInicio),
				LocalDateTime.parse(fechaFin),
				prioridad,
				new BigDecimal(importe),
				"EUR");
	}
}
