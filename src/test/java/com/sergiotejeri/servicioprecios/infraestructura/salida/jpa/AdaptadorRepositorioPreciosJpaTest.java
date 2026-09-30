package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdaptadorRepositorioPreciosJpaTest {

    private final RepositorioPrecios repositorioPrecios;
    private final RepositorioPreciosJpa repositorioPreciosJpa;

    @Autowired
    AdaptadorRepositorioPreciosJpaTest(
            RepositorioPrecios repositorioPrecios,
            RepositorioPreciosJpa repositorioPreciosJpa) {
        this.repositorioPrecios = repositorioPrecios;
        this.repositorioPreciosJpa = repositorioPreciosJpa;
    }

    @BeforeEach
    void eliminaLosPreciosPersistidos() {
        repositorioPreciosJpa.deleteAll();
    }

    @Test
    void recuperaSoloElPrecioAplicableConMayorPrioridad() {
        repositorioPreciosJpa.saveAll(List.of(
                precio(1, 1, 35455, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 0),
                precio(1, 2, 35455, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 1),
                precio(2, 3, 35455, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1),
                precio(1, 4, 99999, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1),
                precio(1, 5, 35455, LocalDateTime.of(2020, 6, 13, 0, 0), LocalDateTime.of(2020, 6, 13, 23, 59, 59), 2)));

        var precio = repositorioPrecios.buscarPrecioAplicable(
                LocalDateTime.of(2020, 6, 14, 16, 0), 35455, 1);

        assertEquals(2, precio.orElseThrow().idTarifa());
    }

    private PrecioJpa precio(
            int idMarca,
            int idTarifa,
            int idProducto,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            int prioridad) {
        return new PrecioJpa(null, idMarca, idTarifa, idProducto, fechaInicio, fechaFin,
                prioridad, new BigDecimal("35.50"), "EUR");
    }
}
