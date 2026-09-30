package com.sergiotejeri.servicioprecios.aplicacion.servicio;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultarPrecioAplicableServiceTest {

    @Test
    void devuelveElPrecioAplicableProporcionadoPorElPuerto() {
        var fechaAplicacion = LocalDateTime.of(2020, 6, 14, 16, 0);
        var precioAplicableEsperado = new Precio(
                2L, 1, 2, 35455,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1, new BigDecimal("25.45"), "EUR");

        RepositorioPrecios repositorio = (fecha, idProducto, idMarca) ->
                Optional.of(precioAplicableEsperado);
        var servicio = new ConsultarPrecioAplicableService(repositorio);

        var precioAplicable = servicio.consultar(fechaAplicacion, 35455, 1);

        assertEquals(2, precioAplicable.idTarifa());
        assertEquals(new BigDecimal("25.45"), precioAplicable.importe());
    }

    @Test
    void informaCuandoElPuertoNoEncuentraUnPrecioAplicable() {
        RepositorioPrecios repositorio = (fecha, idProducto, idMarca) -> Optional.empty();
        var servicio = new ConsultarPrecioAplicableService(repositorio);

        assertThrows(PrecioNoEncontradoException.class, () ->
                servicio.consultar(LocalDateTime.of(2020, 6, 14, 16, 0), 35455, 1));
    }
}
