package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ListaReservasWrapper;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReservaLibroDAOXml {

    private static final String FICHERO_XML = "reservas.xml";

    public void guardar(List<ReservaLibro> reservas) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ListaReservasWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ListaReservasWrapper wrapper = new ListaReservasWrapper(reservas);
        marshaller.marshal(wrapper, new File(FICHERO_XML));
    }

    public List<ReservaLibro> cargar() throws JAXBException {
        File file = new File(FICHERO_XML);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        JAXBContext context = JAXBContext.newInstance(ListaReservasWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ListaReservasWrapper wrapper = (ListaReservasWrapper) unmarshaller.unmarshal(file);
        
        return wrapper.getReservas() != null ? wrapper.getReservas() : new ArrayList<>();
    }
}
