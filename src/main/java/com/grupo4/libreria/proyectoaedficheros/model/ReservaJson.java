package com.grupo4.libreria.proyectoaedficheros.model;

import java.time.LocalDate;

/**
 * Modelo de datos para representar una Reserva en formato JSON.
 * Esta clase actúa como un POJO (Plain Old Java Object) para la librería GSON.
 */
public class ReservaJson {
  // Atributos de la reserva
  String tituloLibro;
  String nombreSolicitante;
  // Nota: LocalDate requiere un adaptador personalizado en GSON para serializarse
  // correctamente
  LocalDate fechaReserva;
  LocalDate fechaDevolucion;

  /**
   * Constructor completo para inicializar todos los campos de la reserva.
   */
  public ReservaJson(String tituloLibro, String nombreSolicitante, LocalDate fechaReserva, LocalDate fechaDevolucion) {
    this.tituloLibro = tituloLibro;
    this.nombreSolicitante = nombreSolicitante;
    this.fechaReserva = fechaReserva;
    this.fechaDevolucion = fechaDevolucion;
  }

  // --- Métodos Getter y Setter ---

  public String getTituloLibro() {
    return tituloLibro;
  }

  public void setTituloLibro(String tituloLibro) {
    this.tituloLibro = tituloLibro;
  }

  public String getNombreSolicitante() {
    return nombreSolicitante;
  }

  public void setNombreSolicitante(String nombreSolicitante) {
    this.nombreSolicitante = nombreSolicitante;
  }

  public LocalDate getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(LocalDate fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public LocalDate getFechaDevolucion() {
    return fechaDevolucion;
  }

  public void setFechaDevolucion(LocalDate fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
  }

  /**
   * Representación textual del objeto formateada como un objeto JSON.
   */
  @Override
  public String toString() {
    return String.format(
        "JSON_Entry { \"tituloLibro\": \"%s\", \"nombreSolicitante\": \"%s\", \"fechaReserva\": \"%s\", \"fechaDevolucion\": \"%s\" }",
        tituloLibro, nombreSolicitante, fechaReserva, fechaDevolucion);
  }
}