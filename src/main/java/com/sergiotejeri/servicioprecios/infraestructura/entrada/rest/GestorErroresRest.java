package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GestorErroresRest {

	@ExceptionHandler(ConstraintViolationException.class)
	ProblemDetail parametroNoValido(ConstraintViolationException excepcion) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Los identificadores deben ser mayores que cero.");
	}

	@ExceptionHandler(NoSuchElementException.class)
	ProblemDetail precioNoEncontrado(NoSuchElementException excepcion) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "No existe un precio aplicable para los datos solicitados.");
	}
}
