package com.sergiotejeri.servicioprecios.infraestructura.configuracion;

import com.sergiotejeri.servicioprecios.infraestructura.salida.jpa.RepositorioPreciosJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:datos_iniciales;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
class DatosInicialesH2Test {

    private final RepositorioPreciosJpa repositorioPreciosJpa;
    private final CommandLineRunner cargarPreciosIniciales;

    @Autowired
    DatosInicialesH2Test(
            RepositorioPreciosJpa repositorioPreciosJpa,
            CommandLineRunner cargarPreciosIniciales) {
        this.repositorioPreciosJpa = repositorioPreciosJpa;
        this.cargarPreciosIniciales = cargarPreciosIniciales;
    }

    @Test
    void cargaLosCuatroPreciosDefinidosEnElEnunciado() {
        var precios = repositorioPreciosJpa.findAll();

        assertEquals(4, precios.size());
        assertTrue(precios.stream().anyMatch(precio ->
                precio.idMarca() == 1
                        && precio.idTarifa() == 1
                        && precio.idProducto() == 35455
                        && precio.fechaInicio().equals(LocalDateTime.of(2020, 6, 14, 0, 0))
                        && precio.fechaFin().equals(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        && precio.prioridad() == 0
                        && precio.importe().compareTo(new BigDecimal("35.50")) == 0
                        && precio.moneda().equals("EUR")));
        assertTrue(precios.stream().anyMatch(precio ->
                precio.idTarifa() == 2
                        && precio.fechaInicio().equals(LocalDateTime.of(2020, 6, 14, 15, 0))
                        && precio.fechaFin().equals(LocalDateTime.of(2020, 6, 14, 18, 30))
                        && precio.prioridad() == 1
                        && precio.importe().compareTo(new BigDecimal("25.45")) == 0));
        assertTrue(precios.stream().anyMatch(precio ->
                precio.idTarifa() == 3
                        && precio.fechaInicio().equals(LocalDateTime.of(2020, 6, 15, 0, 0))
                        && precio.fechaFin().equals(LocalDateTime.of(2020, 6, 15, 11, 0))
                        && precio.importe().compareTo(new BigDecimal("30.50")) == 0));
        assertTrue(precios.stream().anyMatch(precio ->
                precio.idTarifa() == 4
                        && precio.fechaInicio().equals(LocalDateTime.of(2020, 6, 15, 16, 0))
                        && precio.fechaFin().equals(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        && precio.importe().compareTo(new BigDecimal("38.95")) == 0));
    }

    @Test
    void noDuplicaLosPreciosCuandoLaCargaInicialSeEjecutaMasDeUnaVez() throws Exception {
        cargarPreciosIniciales.run();

        assertEquals(4, repositorioPreciosJpa.count());
    }
}
