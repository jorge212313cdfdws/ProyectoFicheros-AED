package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaCsv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaCsv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservaCsvController {

  // ! Ruta del archivo CSV de reservas
  private static final String RUTA_CSV = "ficheros\\ReservasCsv.csv";

  public static void main(String[] args) {
    // ? Espacio para pruebas
    getAllReservas();
  }

  // * --- MÉTODOS AUXILIARES (PERSISTENCIA) --- *

  /**
   * Centraliza la lectura del CSV convirtiendo las filas en objetos ReservaCsv.
   */
  private static List<ReservaCsv> obtenerListaReservas() {
    File archivo = new File(RUTA_CSV);
    if (!archivo.exists() || archivo.length() == 0) {
      return new ArrayList<>();
    }

    try (FileReader reader = new FileReader(RUTA_CSV)) {
      return new CsvToBeanBuilder<ReservaCsv>(reader)
          .withType(ReservaCsv.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build()
          .parse();
    } catch (IOException e) {
      System.err.println("Error al acceder al archivo CSV: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Centraliza la escritura, aplicando la estrategia de mapeo y el formato de
   * comillas.
   */
  private static void guardarListaReservas(List<ReservaCsv> lista) {
    try (FileWriter writer = new FileWriter(RUTA_CSV)) {
      HeaderColumnNameMappingStrategy<ReservaCsv> strategy = new HeaderColumnNameMappingStrategy<>();
      strategy.setType(ReservaCsv.class);

      StatefulBeanToCsv<ReservaCsv> beanToCsv = new StatefulBeanToCsvBuilder<ReservaCsv>(writer)
          .withSeparator(',')
          .withMappingStrategy(strategy)
          .withApplyQuotesToAll(true)
          .build();

      beanToCsv.write(lista);
    } catch (Exception e) {
      System.err.println("Error al guardar en el CSV: " + e.getMessage());
    }
  }

  // * --- MÉTODOS PÚBLICOS (CRUD) --- *

  // * Lee todas las reservas y las imprime por consola
  public static void getAllReservas() {
    List<ReservaCsv> lista = obtenerListaReservas();
    System.out.println("--- Listado de Reservas (CSV) ---");
    if (lista.isEmpty()) {
      System.out.println("No hay reservas en el archivo.");
    } else {
      lista.forEach(System.out::println);
    }
  }

  
  // * Busca y devuelve una única reserva por el nombre del solicitante
  public static ReservaCsv getReservaBySolicitante(String nombre) {
    List<ReservaCsv> lista = obtenerListaReservas();
    for (ReservaCsv r : lista) {
      if (r.getNombreSolicitante().equalsIgnoreCase(nombre)) {
        return r;
      }
    }
    return null;
  }

  // * Añade una reserva nueva al final del archivo sincronizado
  public static void addReserva(ReservaCsv nuevaReserva) {
    List<ReservaCsv> lista = obtenerListaReservas();
    lista.add(nuevaReserva);
    guardarListaReservas(lista);
    System.out.println("Reserva de '" + nuevaReserva.getTituloLibro() + "' añadida al CSV.");
  }

  // * Actualiza una reserva buscando por su título original
  public static void updateReserva(String tituloBusqueda, ReservaCsv nuevosDatos) {
    List<ReservaCsv> lista = obtenerListaReservas();
    boolean encontrado = false;

    for (ReservaCsv r : lista) {
      if (r.getTituloLibro().equalsIgnoreCase(tituloBusqueda)) {
        r.setTituloLibro(nuevosDatos.getTituloLibro());
        r.setNombreSolicitante(nuevosDatos.getNombreSolicitante());
        r.setFechaReserva(nuevosDatos.getFechaReserva());
        r.setFechaDevolucion(nuevosDatos.getFechaDevolucion());
        encontrado = true;
        break;
      }
    }

    if (encontrado) {
      guardarListaReservas(lista);
      System.out.println("Reserva '" + tituloBusqueda + "' actualizada con éxito.");
    } else {
      System.out.println("No se encontró el libro: " + tituloBusqueda);
    }
  }

  // * Elimina una reserva del archivo
  public static void deleteReservaBySolicitante(String nombre) {
    List<ReservaCsv> lista = obtenerListaReservas();
    if (lista.removeIf(r -> r.getNombreSolicitante().equalsIgnoreCase(nombre))) {
      guardarListaReservas(lista);
      System.out.println("Reserva de " + nombre + " eliminada del CSV.");
    } else {
      System.out.println("No se encontró ninguna reserva para: " + nombre);
    }
  }
}