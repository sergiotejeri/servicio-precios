package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultarPrecioAplicableRestTest {

	private final MockMvc clienteHttp;

	@Autowired
	ConsultarPrecioAplicableRestTest(MockMvc clienteHttp) {
		this.clienteHttp = clienteHttp;
	}

	@Test
	void devuelveElPrecioAplicableMedianteGet() throws Exception {
		clienteHttp.perform(get("/precios")
				.param("fechaAplicacion", "2020-06-14T16:00:00")
				.param("idProducto", "35455")
				.param("idMarca", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idProducto").value(35455))
			.andExpect(jsonPath("$.idMarca").value(1))
			.andExpect(jsonPath("$.idTarifa").value(2))
			.andExpect(jsonPath("$.fechaInicio").value("2020-06-14T15:00:00"))
			.andExpect(jsonPath("$.fechaFin").value("2020-06-14T18:30:00"))
			.andExpect(jsonPath("$.importe").value(25.45))
			.andExpect(jsonPath("$.moneda").value("EUR"));
	}
}
