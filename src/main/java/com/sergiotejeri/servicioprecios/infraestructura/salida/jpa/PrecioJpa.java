package com.sergiotejeri.servicioprecios.infraestructura.salida.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Representa una fila de PRICES; los detalles de persistencia quedan fuera del dominio. */
@Entity
@Table(
    name = "PRICES",
    indexes =
        @Index(
            name = "idx_prices_product_brand_dates_priority",
            columnList = "PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY"))
public class PrecioJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "BRAND_ID", nullable = false)
  private int idMarca;

  @Column(name = "PRICE_LIST", nullable = false)
  private int idTarifa;

  @Column(name = "PRODUCT_ID", nullable = false)
  private int idProducto;

  @Column(name = "START_DATE", nullable = false)
  private LocalDateTime fechaInicio;

  @Column(name = "END_DATE", nullable = false)
  private LocalDateTime fechaFin;

  @Column(name = "PRIORITY", nullable = false)
  private int prioridad;

  @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
  private BigDecimal importe;

  @Column(name = "CURR", nullable = false, length = 3)
  private String moneda;

  protected PrecioJpa() {}

  /**
   * Construye una fila de precios para su persistencia.
   *
   * @param id identificador de la fila; nulo antes de persistir
   * @param idMarca cadena a la que pertenece el producto
   * @param idTarifa tarifa comercial
   * @param idProducto producto al que se aplica la tarifa
   * @param fechaInicio inicio inclusivo de la vigencia
   * @param fechaFin fin inclusivo de la vigencia
   * @param prioridad precedencia frente a otras tarifas vigentes
   * @param importe precio final de venta
   * @param moneda código ISO de la moneda
   */
  public PrecioJpa(
      Long id,
      int idMarca,
      int idTarifa,
      int idProducto,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      int prioridad,
      BigDecimal importe,
      String moneda) {
    this.id = id;
    this.idMarca = idMarca;
    this.idTarifa = idTarifa;
    this.idProducto = idProducto;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.prioridad = prioridad;
    this.importe = importe;
    this.moneda = moneda;
  }

  public Long id() {
    return id;
  }

  public int idMarca() {
    return idMarca;
  }

  public int idTarifa() {
    return idTarifa;
  }

  public int idProducto() {
    return idProducto;
  }

  public LocalDateTime fechaInicio() {
    return fechaInicio;
  }

  public LocalDateTime fechaFin() {
    return fechaFin;
  }

  public int prioridad() {
    return prioridad;
  }

  public BigDecimal importe() {
    return importe;
  }

  public String moneda() {
    return moneda;
  }
}
