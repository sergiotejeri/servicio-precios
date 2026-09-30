package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultarPrecioAplicableRestTest {

  private final MockMvc clienteHttp;

  @Autowired
  ConsultarPrecioAplicableRestTest(MockMvc clienteHttp) {
    this.clienteHttp = clienteHttp;
  }

  @ParameterizedTest
  @MethodSource("escenariosRequeridos")
  void devuelveElPrecioAplicableParaCadaEscenarioRequerido(Escenario escenario) throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", escenario.fechaAplicacion())
                .param("idProducto", "35455")
                .param("idMarca", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idProducto").value(35455))
        .andExpect(jsonPath("$.idMarca").value(1))
        .andExpect(jsonPath("$.idTarifa").value(escenario.idTarifa()))
        .andExpect(jsonPath("$.fechaInicio").value(escenario.fechaInicio()))
        .andExpect(jsonPath("$.fechaFin").value(escenario.fechaFin()))
        .andExpect(jsonPath("$.importe").value(escenario.importe()))
        .andExpect(jsonPath("$.moneda").value("EUR"));
  }

  private static Stream<Escenario> escenariosRequeridos() {
    return Stream.of(
        new Escenario(
            "2020-06-14T10:00:00", 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 35.50),
        new Escenario(
            "2020-06-14T15:00:00", 2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 25.45),
        new Escenario(
            "2020-06-14T16:00:00", 2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 25.45),
        new Escenario(
            "2020-06-14T18:30:00", 2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 25.45),
        new Escenario(
            "2020-06-14T21:00:00", 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 35.50),
        new Escenario(
            "2020-06-15T10:00:00", 3, "2020-06-15T00:00:00", "2020-06-15T11:00:00", 30.50),
        new Escenario(
            "2020-06-16T21:00:00", 4, "2020-06-15T16:00:00", "2020-12-31T23:59:59", 38.95));
  }

  @Test
  void rechazaUnIdentificadorDeProductoNoValido() throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", "2020-06-14T10:00:00")
                .param("idProducto", "0")
                .param("idMarca", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void rechazaUnIdentificadorDeMarcaNoValido() throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", "2020-06-14T10:00:00")
                .param("idProducto", "35455")
                .param("idMarca", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void rechazaUnaPeticionSinFechaDeAplicacion() throws Exception {
    clienteHttp
        .perform(get("/precios").param("idProducto", "35455").param("idMarca", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.detail")
                .value("La fecha de aplicación es obligatoria y debe tener formato ISO-8601."));
  }

  @Test
  void rechazaUnaFechaDeAplicacionConFormatoInvalido() throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", "14-06-2020 10:00")
                .param("idProducto", "35455")
                .param("idMarca", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.detail")
                .value("La fecha de aplicación es obligatoria y debe tener formato ISO-8601."));
  }

  @Test
  void informaCuandoNoExisteUnPrecioAplicable() throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", "2020-06-14T10:00:00")
                .param("idProducto", "99999")
                .param("idMarca", "1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @ParameterizedTest
  @ValueSource(strings = {"idProducto", "idMarca"})
  void informaDelIdentificadorAusente(String parametro) throws Exception {
    var peticion = get("/precios").param("fechaAplicacion", "2020-06-14T10:00:00");
    if (parametro.equals("idProducto")) {
      peticion.param("idMarca", "1");
    } else {
      peticion.param("idProducto", "35455");
    }
    clienteHttp
        .perform(peticion)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("El parámetro " + parametro + " es obligatorio."));
  }

  @ParameterizedTest
  @ValueSource(strings = {"idProducto", "idMarca"})
  void informaDelIdentificadorConFormatoInvalido(String parametro) throws Exception {
    clienteHttp
        .perform(
            get("/precios")
                .param("fechaAplicacion", "2020-06-14T10:00:00")
                .param("idProducto", parametro.equals("idProducto") ? "abc" : "35455")
                .param("idMarca", parametro.equals("idMarca") ? "abc" : "1"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.detail")
                .value("El parámetro " + parametro + " debe ser un número entero."));
  }

  private record Escenario(
      String fechaAplicacion, int idTarifa, String fechaInicio, String fechaFin, double importe) {}
}
