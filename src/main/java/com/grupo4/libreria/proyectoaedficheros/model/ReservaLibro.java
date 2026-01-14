package com.grupo4.libreria.proyectoaedficheros.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Modelo que representa una reserva de un libro en la biblioteca.
 * Incluye validaciones de datos según los requisitos del proyecto.
 */
public class ReservaLibro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tituloLibro;
    private String nombreSolicitante;
    private LocalDate fechaReserva;
    private LocalDate fechaDevolucion;

    /**
     * Constructor vacío.
     * Necesario para JSON, XML y serialización.
     */
    public ReservaLibro() {
    }

    /**
     * Constructor completo con validación de datos.
     */
    public ReservaLibro(String tituloLibro, String nombreSolicitante,
                        LocalDate fechaReserva, LocalDate fechaDevolucion) {
        this.tituloLibro = tituloLibro;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
        validar();
    }

    // =======================
    // VALIDACIONES
    // =======================

    /**
     * Valida que los datos de la reserva sean correctos.
     * Lanza IllegalArgumentException si algún dato no es válido.
     */
    public void validar() {

        if (tituloLibro == null || tituloLibro.isBlank()) {
            throw new IllegalArgumentException(
                    "El título del libro no puede estar vacío"
            );
        }

        if (nombreSolicitante == null || nombreSolicitante.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del solicitante no puede estar vacío"
            );
        }

        if (fechaReserva == null || fechaDevolucion == null) {
            throw new IllegalArgumentException(
                    "Las fechas de reserva y devolución no pueden ser nulas"
            );
        }

        if (fechaDevolucion.isBefore(fechaReserva)) {
            throw new IllegalArgumentException(
                    "La fecha de devolución no puede ser anterior a la fecha de reserva"
            );
        }
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
        validar();
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
        validar();
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
        validar();
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
        validar();
    }

    // =======================
    // MÉTODOS AUXILIARES
    // =======================

    @Override
    public String toString() {
        return "ReservaLibro{" +
                "tituloLibro='" + tituloLibro + '\'' +
                ", nombreSolicitante='" + nombreSolicitante + '\'' +
                ", fechaReserva=" + fechaReserva +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}
