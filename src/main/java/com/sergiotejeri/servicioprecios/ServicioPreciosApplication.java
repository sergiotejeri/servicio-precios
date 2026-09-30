package com.sergiotejeri.servicioprecios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Arranca el servicio HTTP de consulta de precios. */
@SpringBootApplication
public class ServicioPreciosApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServicioPreciosApplication.class, args);
  }
}
