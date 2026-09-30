package com.sergiotejeri.servicioprecios.infraestructura.configuracion;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.entrada.ConsultarPrecioAplicable;
import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.aplicacion.servicio.ConsultarPrecioAplicableService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionAplicacion {

    @Bean
    ConsultarPrecioAplicable consultarPrecioAplicable(RepositorioPrecios repositorioPrecios) {
        return new ConsultarPrecioAplicableService(repositorioPrecios);
    }
}
