package com.grupo4.libreria.proyectoaedficheros.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "reservas")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaReservasWrapper {

    @XmlElement(name = "reserva")
    private List<ReservaLibro> reservas;

    public ListaReservasWrapper() {
    }

    public ListaReservasWrapper(List<ReservaLibro> reservas) {
        this.reservas = reservas;
    }

    public List<ReservaLibro> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaLibro> reservas) {
        this.reservas = reservas;
    }
}
