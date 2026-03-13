package com.grupo4.libreria.proyectoaedficheros.model;

import com.grupo4.libreria.proyectoaedficheros.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "reserva")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservaLibro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tituloLibro;
    private String nombreSolicitante;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaReserva;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaDevolucion;

    public ReservaLibro() {
    }

    public ReservaLibro(String tituloLibro, String nombreSolicitante,
                        LocalDate fechaReserva, LocalDate fechaDevolucion) {
        this.tituloLibro = tituloLibro;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
        validar();
    }

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

        if (fechaReserva == null) {
            throw new IllegalArgumentException(
                    "La fecha de reserva no puede ser nula"
            );
        }

        if (fechaDevolucion != null && fechaDevolucion.isBefore(fechaReserva)) {
            throw new IllegalArgumentException(
                    "La fecha de devolución no puede ser anterior a la fecha de reserva"
            );
        }
    }

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
