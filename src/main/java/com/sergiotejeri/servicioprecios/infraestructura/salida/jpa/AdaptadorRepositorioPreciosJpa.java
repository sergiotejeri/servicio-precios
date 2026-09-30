package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import com.sergiotejeri.servicioprecios.aplicacion.puerto.salida.RepositorioPrecios;
import com.sergiotejeri.servicioprecios.dominio.Precio;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** Implementa el puerto de precios y convierte las entidades JPA al modelo de dominio. */
@Repository
public class AdaptadorRepositorioPreciosJpa implements RepositorioPrecios {

  private final RepositorioPreciosJpa repositorioPreciosJpa;

  public AdaptadorRepositorioPreciosJpa(RepositorioPreciosJpa repositorioPreciosJpa) {
    this.repositorioPreciosJpa = repositorioPreciosJpa;
  }

  @Override
  public Optional<Precio> buscarPrecioAplicable(
      LocalDateTime fechaAplicacion, int idProducto, int idMarca) {
    return repositorioPreciosJpa
        .buscarPrecioAplicable(idProducto, idMarca, fechaAplicacion)
        .map(this::convertirEnPrecio);
  }

  private Precio convertirEnPrecio(PrecioJpa precio) {
    return new Precio(
        precio.id(),
        precio.idMarca(),
        precio.idTarifa(),
        precio.idProducto(),
        precio.fechaInicio(),
        precio.fechaFin(),
        precio.prioridad(),
        precio.importe(),
        precio.moneda());
  }
}
