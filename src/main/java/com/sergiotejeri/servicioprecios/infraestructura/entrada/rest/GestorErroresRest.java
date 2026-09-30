package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import com.sergiotejeri.servicioprecios.aplicacion.servicio.PrecioNoEncontradoException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Traduce errores de entrada y precios ausentes a respuestas HTTP ProblemDetail. */
@RestControllerAdvice
public class GestorErroresRest {

  @ExceptionHandler(ConstraintViolationException.class)
  ProblemDetail parametroNoValido(ConstraintViolationException excepcion) {
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST, "Los identificadores deben ser mayores que cero.");
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  ProblemDetail parametroAusente(MissingServletRequestParameterException excepcion) {
    if ("fechaAplicacion".equals(excepcion.getParameterName())) {
      return fechaAplicacionNoValida();
    }
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        "El parámetro " + excepcion.getParameterName() + " es obligatorio.");
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ProblemDetail formatoNoValido(MethodArgumentTypeMismatchException excepcion) {
    if ("fechaAplicacion".equals(excepcion.getName())) {
      return fechaAplicacionNoValida();
    }
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        "El parámetro " + excepcion.getName() + " debe ser un número entero.");
  }

  private ProblemDetail fechaAplicacionNoValida() {
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        "La fecha de aplicación es obligatoria y debe tener formato ISO-8601.");
  }

  @ExceptionHandler(PrecioNoEncontradoException.class)
  ProblemDetail precioNoEncontrado(PrecioNoEncontradoException excepcion) {
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND, "No existe un precio aplicable para los datos solicitados.");
  }
}
