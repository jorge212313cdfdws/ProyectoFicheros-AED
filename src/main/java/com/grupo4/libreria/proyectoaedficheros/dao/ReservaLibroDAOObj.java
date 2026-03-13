package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaLibroDAOObj {

    private static final String FICHERO_OBJ = "reservas.bin";

    public void guardar(List<ReservaLibro> reservas) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_OBJ))) {
            oos.writeObject(reservas);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ReservaLibro> cargar() throws IOException, ClassNotFoundException {
        File file = new File(FICHERO_OBJ);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<ReservaLibro>) ois.readObject();
        }
    }
}
