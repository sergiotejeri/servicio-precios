# Servicio de precios

Prueba técnica en Spring Boot para consultar el precio aplicable a un producto de una marca en una fecha concreta.

La aplicación recibe una fecha, el identificador del producto y el identificador de la marca. Devuelve un único precio: el que está vigente en ese momento y tiene mayor prioridad.

## Requisitos

- Java 21
- No es necesario instalar una base de datos. La aplicación usa H2 en memoria.

## Arranque

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

## Pruebas

Para compilar el proyecto y ejecutar todas las pruebas:

```powershell
.\mvnw.cmd clean verify
```

El comando también valida el estilo y genera el informe de cobertura en
`target/site/jacoco/index.html`. Además, exige una cobertura mínima de líneas
del 90 %.

## Consulta de precio

La consulta se realiza con `GET /precios` y requiere estos parámetros:

- `fechaAplicacion`: fecha y hora en formato ISO-8601.
- `idProducto`: identificador del producto.
- `idMarca`: identificador de la marca.

Ejemplo:

```http
GET /precios?fechaAplicacion=2020-06-14T16:00:00&idProducto=35455&idMarca=1
```

Respuesta:

```json
{
  "idProducto": 35455,
  "idMarca": 1,
  "idTarifa": 2,
  "fechaInicio": "2020-06-14T15:00:00",
  "fechaFin": "2020-06-14T18:30:00",
  "importe": 25.45,
  "moneda": "EUR"
}
```

## Errores

Si un identificador no es válido, la aplicación responde `400 Bad Request`:

```json
{
  "status": 400,
  "detail": "Los identificadores deben ser mayores que cero."
}
```

Si no existe un precio aplicable, responde `404 Not Found`:

```json
{
  "status": 404,
  "detail": "No existe un precio aplicable para los datos solicitados."
}
```

## Datos cargados

Al arrancar se cargan en H2 los cuatro precios indicados en el enunciado. Las pruebas cubren los cinco casos solicitados:

| Fecha de aplicación | Tarifa esperada | Importe esperado |
| --- | ---: | ---: |
| 2020-06-14 10:00:00 | 1 | 35.50 EUR |
| 2020-06-14 16:00:00 | 2 | 25.45 EUR |
| 2020-06-14 21:00:00 | 1 | 35.50 EUR |
| 2020-06-15 10:00:00 | 3 | 30.50 EUR |
| 2020-06-16 21:00:00 | 4 | 38.95 EUR |

También se comprueba que los límites de inicio y fin de una vigencia son inclusivos.

## Arquitectura

El proyecto sigue una arquitectura hexagonal sencilla:

- `dominio`: contiene el modelo `Precio` y no depende de Spring, JPA ni HTTP.
- `aplicacion`: contiene el caso de uso y los puertos que definen cómo se consulta un precio.
- `infraestructura`: contiene los adaptadores. En este caso, el controlador REST, la configuración de Spring, la carga inicial de H2 y el adaptador JPA.

El caso de uso depende de un puerto de salida (`RepositorioPrecios`), no de JPA. Por eso el acceso a datos puede cambiar sin modificar la lógica de selección del precio.

La consulta JPA filtra por producto, marca y rango de fechas, ordena por prioridad y limita el resultado al primer precio. El puerto de salida devuelve un `Optional<Precio>`, porque una consulta puede no tener resultado, pero nunca devuelve más de uno.

## Decisiones principales

- Las fechas de inicio y fin forman parte del periodo de vigencia.
- Cuando existen varios precios vigentes, gana el de mayor prioridad.
- H2 permite arrancar y probar el proyecto sin dependencias externas.
- Las pruebas cubren la lógica de aplicación, el acceso JPA, la carga de datos y el endpoint HTTP.
- La configuración desactiva `open-in-view` para evitar consultas a la base de datos fuera de la capa de persistencia.
- La entidad JPA tiene un índice para producto, marca, periodo de vigencia y prioridad, que acompaña a la consulta más frecuente.
- El dominio valida que un precio tenga identificadores válidos, moneda ISO, un periodo coherente e importe no negativo antes de usarlo.
- Maven valida estilo sin tabuladores, imports con comodín o bloques sin llaves, y genera un informe de cobertura en cada `verify`.
