package com.grupo4.libreria.proyectoaedficheros.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "reservas")
public class ReservaXmlWrapper {
    private List<ReservaXml> reservas;

    @XmlElement(name = "reserva")
    public List<ReservaXml> getReservas() { return reservas; }
    public void setReservas(List<ReservaXml> reservas) { this.reservas = reservas; }
}