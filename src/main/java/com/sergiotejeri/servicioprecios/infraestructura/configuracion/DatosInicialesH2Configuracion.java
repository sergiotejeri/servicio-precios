package com.sergiotejeri.servicioprecios.infraestructura.configuracion;

import com.sergiotejeri.servicioprecios.infraestructura.salida.jpa.PrecioJpa;
import com.sergiotejeri.servicioprecios.infraestructura.salida.jpa.RepositorioPreciosJpa;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Carga los cuatro precios del enunciado cuando la base de datos está vacía. */
@Configuration
public class DatosInicialesH2Configuracion {

  @Bean
  CommandLineRunner cargarPreciosIniciales(RepositorioPreciosJpa repositorioPreciosJpa) {
    return argumentos -> {
      if (repositorioPreciosJpa.count() == 0) {
        repositorioPreciosJpa.saveAll(
            List.of(
                precio(
                    1,
                    1,
                    35455,
                    fecha(2020, 6, 14, 0, 0),
                    fecha(2020, 12, 31, 23, 59, 59),
                    0,
                    "35.50"),
                precio(
                    1, 2, 35455, fecha(2020, 6, 14, 15, 0), fecha(2020, 6, 14, 18, 30), 1, "25.45"),
                precio(
                    1, 3, 35455, fecha(2020, 6, 15, 0, 0), fecha(2020, 6, 15, 11, 0), 1, "30.50"),
                precio(
                    1,
                    4,
                    35455,
                    fecha(2020, 6, 15, 16, 0),
                    fecha(2020, 12, 31, 23, 59, 59),
                    1,
                    "38.95")));
      }
    };
  }

  private PrecioJpa precio(
      int idMarca,
      int idTarifa,
      int idProducto,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      int prioridad,
      String importe) {
    return new PrecioJpa(
        null,
        idMarca,
        idTarifa,
        idProducto,
        fechaInicio,
        fechaFin,
        prioridad,
        new BigDecimal(importe),
        "EUR");
  }

  private LocalDateTime fecha(int anio, int mes, int dia, int hora, int minuto) {
    return LocalDateTime.of(anio, mes, dia, hora, minuto);
  }

  private LocalDateTime fecha(int anio, int mes, int dia, int hora, int minuto, int segundo) {
    return LocalDateTime.of(anio, mes, dia, hora, minuto, segundo);
  }
}
