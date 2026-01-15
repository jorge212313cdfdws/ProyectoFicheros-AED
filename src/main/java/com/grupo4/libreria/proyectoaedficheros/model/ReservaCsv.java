package com.grupo4.libreria.proyectoaedficheros.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

public class ReservaCsv {

    @CsvBindByName(column = "tituloLibro")
    private String tituloLibro;

    @CsvBindByName(column = "nombreSolicitante")
    private String nombreSolicitante;

    @CsvBindByName(column = "fechaReserva")
    @CsvDate("yyyy-MM-dd") // Formato estándar ISO
    private LocalDate fechaReserva;

    @CsvBindByName(column = "fechaDevolucion")
    @CsvDate("yyyy-MM-dd")
    private LocalDate fechaDevolucion;

    public ReservaCsv() {}

    public ReservaCsv(String tituloLibro, String nombreSolicitante, LocalDate fechaReserva, LocalDate fechaDevolucion) {
        this.tituloLibro = tituloLibro;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
    }

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

    @Override
    public String toString() {
        return "Reserva [tituloLibro=" + tituloLibro + ", nombreSolicitante=" + nombreSolicitante
                + ", fechaReserva=" + fechaReserva + ", fechaDevolucion=" + fechaDevolucion + "]";
    }
}