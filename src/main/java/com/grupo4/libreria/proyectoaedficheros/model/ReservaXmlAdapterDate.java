package com.grupo4.libreria.proyectoaedficheros.model;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class ReservaXmlAdapterDate extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) { return LocalDate.parse(v); }
    public String marshal(LocalDate v) { return v.toString(); }
}