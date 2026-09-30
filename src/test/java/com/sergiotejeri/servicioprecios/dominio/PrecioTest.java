package com.sergiotejeri.servicioprecios.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PrecioTest {

    @Test
    void rechazaIdentificadoresNoPositivos() {
        assertThrows(IllegalArgumentException.class, () -> new Precio(
                1L,
                0,
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                new BigDecimal("35.50"),
                "EUR"));
    }

    @Test
    void rechazaUnaPrioridadNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Precio(
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
        assertThrows(IllegalArgumentException.class, () -> new Precio(
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
        assertThrows(IllegalArgumentException.class, () -> new Precio(
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
    void rechazaUnaMonedaQueNoEsUnCodigoISO() {
        assertThrows(IllegalArgumentException.class, () -> new Precio(
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

    @Test
    void rechazaUnaFechaDeInicioAusente() {
        assertThrows(NullPointerException.class, () -> new Precio(
                1L,
                1,
                1,
                35455,
                null,
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,
                new BigDecimal("35.50"),
                "EUR"));
    }
}
