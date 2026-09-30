package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada.ConsultarPrecioAplicable;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/precios")
@Validated
public class ControladorPrecios {

    private final ConsultarPrecioAplicable consultarPrecioAplicable;

    public ControladorPrecios(ConsultarPrecioAplicable consultarPrecioAplicable) {
        this.consultarPrecioAplicable = consultarPrecioAplicable;
    }

    @GetMapping
    public RespuestaPrecio consultar(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaAplicacion,
            @RequestParam @Min(1) int idProducto,
            @RequestParam @Min(1) int idMarca) {
        var precio = consultarPrecioAplicable.consultar(fechaAplicacion, idProducto, idMarca);
        return new RespuestaPrecio(
                precio.idProducto(), precio.idMarca(), precio.idTarifa(), precio.fechaInicio(),
                precio.fechaFin(), precio.importe(), precio.moneda());
    }
}
