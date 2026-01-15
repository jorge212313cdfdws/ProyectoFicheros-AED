package com.grupo4.libreria.proyectoaedficheros.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlType(propOrder = {"tituloLibro", "nombreSolicitante", "fechaReserva", "fechaDevolucion"})
public class ReservaXml {
    private String tituloLibro;
    private String nombreSolicitante;
    private LocalDate fechaReserva;
    private LocalDate fechaDevolucion;

    public ReservaXml() {}

    public ReservaXml(String tituloLibro, String nombreSolicitante, LocalDate fechaReserva, LocalDate fechaDevolucion) {
        this.tituloLibro = tituloLibro;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
    }

    @XmlElement
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    @XmlElement
    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }

    @XmlElement
    @XmlJavaTypeAdapter(value = ReservaXmlAdapterDate.class) // Adaptador necesario
    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    @XmlElement
    @XmlJavaTypeAdapter(value = ReservaXmlAdapterDate.class) // Adaptador necesario
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    @Override
    public String toString() {
        return String.format(
                "XML_Node:\n  <reserva>\n    <titulo>%s</titulo>\n    <solicitante>%s</solicitante>\n    <periodo>%s hasta %s</periodo>\n  </reserva>",
                tituloLibro, nombreSolicitante, fechaReserva, fechaDevolucion
        );
    }
}