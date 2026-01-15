package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaLibroDAOCsv {

    private static final String FICHERO_CSV = "reservas.csv";

    public void guardar(List<ReservaLibro> reservas) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FICHERO_CSV))) {
            // Cabecera opcional
            // writer.writeNext(new String[]{"Titulo", "Solicitante", "FechaReserva", "FechaDevolucion"});

            for (ReservaLibro r : reservas) {
                String[] datos = {
                        r.getTituloLibro(),
                        r.getNombreSolicitante(),
                        r.getFechaReserva().toString(),
                        r.getFechaDevolucion().toString()
                };
                writer.writeNext(datos);
            }
        }
    }

    public List<ReservaLibro> cargar() throws IOException, CsvValidationException {
        List<ReservaLibro> lista = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FICHERO_CSV))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                if (linea.length >= 4) {
                    String titulo = linea[0];
                    String solicitante = linea[1];
                    LocalDate fechaRes = LocalDate.parse(linea[2]);
                    LocalDate fechaDev = LocalDate.parse(linea[3]);
                    
                    lista.add(new ReservaLibro(titulo, solicitante, fechaRes, fechaDev));
                }
            }
        } catch (IOException e) {
            // Si el fichero no existe, devolvemos lista vacía
            return new ArrayList<>();
        }
        return lista;
    }
}
