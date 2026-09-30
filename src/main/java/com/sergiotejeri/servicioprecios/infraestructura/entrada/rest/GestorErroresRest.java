package com.sergiotejeri.servicioprecios.infraestructura.entrada.rest;

import com.sergiotejeri.servicioprecios.aplicacion.servicio.PrecioNoEncontradoException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GestorErroresRest {

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail parametroNoValido(ConstraintViolationException excepcion) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Los identificadores deben ser mayores que cero.");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    ProblemDetail fechaAplicacionNoValida(Exception excepcion) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "La fecha de aplicación es obligatoria y debe tener formato ISO-8601.");
    }

    @ExceptionHandler(PrecioNoEncontradoException.class)
    ProblemDetail precioNoEncontrado(PrecioNoEncontradoException excepcion) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "No existe un precio aplicable para los datos solicitados.");
    }
}
