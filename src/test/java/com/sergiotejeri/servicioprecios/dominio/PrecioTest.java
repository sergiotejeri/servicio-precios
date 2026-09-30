package com.sergiotejeri.servicioprecios.dominio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PrecioTest {

  @ParameterizedTest
  @CsvSource({"0, 1, 35455", "-1, 1, 35455", "1, 0, 35455", "1, -1, 35455", "1, 1, 0", "1, 1, -1"})
  void rechazaIdentificadoresNoPositivos(int idMarca, int idTarifa, int idProducto) {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Precio(
                1L,
                idMarca,
                idTarifa,
                idProducto,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                new BigDecimal("35.50"),
                "EUR"));
  }

  @Test
  void rechazaUnaPrioridadNegativa() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Precio(
                1L,
                1,
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                -1,
                new BigDecimal("35.50"),
                "EUR"));
  }

  @Test
  void rechazaUnPeriodoDeVigenciaInvalido() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Precio(
                1L,
                1,
                1,
                35455,
                LocalDateTime.of(2020, 6, 15, 11, 0),
                LocalDateTime.of(2020, 6, 15, 10, 0),
                0,
                new BigDecimal("35.50"),
                "EUR"));
  }

  @Test
  void rechazaUnImporteNegativo() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Precio(
                1L,
                1,
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                new BigDecimal("-0.01"),
                "EUR"));
  }

  @Test
  void rechazaUnaMonedaQueNoEsUnCodigoIso() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Precio(
                1L,
                1,
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                new BigDecimal("35.50"),
                "AAA"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"fechaInicio", "fechaFin", "importe", "moneda"})
  void rechazaUnCampoObligatorioAusente(String campo) {
    assertThrows(
        NullPointerException.class,
        () ->
            new Precio(
                1L,
                1,
                1,
                35455,
                campo.equals("fechaInicio") ? null : LocalDateTime.of(2020, 6, 14, 0, 0),
                campo.equals("fechaFin") ? null : LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                campo.equals("importe") ? null : new BigDecimal("35.50"),
                campo.equals("moneda") ? null : "EUR"));
  }

  @Test
  void aceptaUnPrecioGratuitoDeUnInstanteConPrioridadCero() {
    var fecha = LocalDateTime.of(2020, 6, 14, 0, 0);
    assertDoesNotThrow(() -> new Precio(1L, 1, 1, 35455, fecha, fecha, 0, BigDecimal.ZERO, "EUR"));
  }
}
