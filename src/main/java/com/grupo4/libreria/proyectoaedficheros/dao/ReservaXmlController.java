package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaListWrapper;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReservaXmlController {

  // ! Ruta del archivo XML de reservas
  private static final String RUTA_XML = "src/main/java/org/reservas.xml";

  public static void main(String[] args) {
    // ? Espacio para pruebas
    getAllReservas();
  }

  // * --- MÉTODOS AUXILIARES (PERSISTENCIA) --- *

  /**
   * Centraliza la lectura del XML.
   * Usa el Wrapper para recuperar la lista de objetos.
   */
  private static List<ReservaXml> obtenerListaReservas() {
    File file = new File(RUTA_XML);
    if (!file.exists()) {
      return new ArrayList<>();
    }

    try {
      JAXBContext context = JAXBContext.newInstance(ReservaListWrapper.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();

      // Leemos el "contenedor"
      ReservaListWrapper wrapper = (ReservaListWrapper) unmarshaller.unmarshal(file);
      return (wrapper.getReservas() != null) ? wrapper.getReservas() : new ArrayList<>();

    } catch (Exception e) {
      System.err.println("Error al leer XML: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Centraliza la escritura del XML.
   * Envuelve la lista en el Wrapper y la guarda formateada.
   */
  private static void guardarListaReservas(List<ReservaXml> lista) {
    try {
      File file = new File(RUTA_XML);
      JAXBContext context = JAXBContext.newInstance(ReservaListWrapper.class);
      Marshaller marshaller = context.createMarshaller();

      // Configuración para que el XML sea legible (con saltos de línea)
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      // Creamos el wrapper y le metemos la lista
      ReservaListWrapper wrapper = new ReservaListWrapper();
      wrapper.setReservas(lista);

      // Guardamos el wrapper entero
      marshaller.marshal(wrapper, file);

    } catch (Exception e) {
      System.err.println("Error al guardar XML: " + e.getMessage());
    }
  }

  // * --- MÉTODOS PÚBLICOS (CRUD) --- *

  // * Muestra todas las reservas del XML
  public static void getAllReservas() {
    List<ReservaXml> lista = obtenerListaReservas();
    System.out.println("--- Listado de Reservas (XML) ---");
    if (lista.isEmpty()) {
      System.out.println("El archivo XML está vacío o no existe.");
    } else {
      lista.forEach(System.out::println);
    }
  }

  // * Busca una reserva específica por el nombre del solicitante
  public static ReservaXml getReservaBySolicitante(String nombre) {
    List<ReservaXml> lista = obtenerListaReservas();
    for (ReservaXml r : lista) {
      if (r.getNombreSolicitante().equalsIgnoreCase(nombre)) {
        return r;
      }
    }
    return null;
  }

  // * Añade una nueva reserva al XML
  public static void addReserva(ReservaXml nuevaReserva) {
    List<ReservaXml> lista = obtenerListaReservas();
    lista.add(nuevaReserva);
    guardarListaReservas(lista);
    System.out.println("Reserva añadida al XML correctamente.");
  }

  // * Modifica una reserva existente buscando por el título del libro
  public static void updateReserva(String tituloBusqueda, ReservaXml nuevosDatos) {
    List<ReservaXml> lista = obtenerListaReservas();
    boolean encontrado = false;

    for (ReservaXml r : lista) {
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
      System.out.println("Reserva '" + tituloBusqueda + "' actualizada en el XML.");
    } else {
      System.out.println("No se encontró el libro en el XML.");
    }
  }

  // * Elimina una reserva por el nombre del solicitante
  public static void deleteReservaBySolicitante(String nombre) {
    List<ReservaXml> lista = obtenerListaReservas();
    if (lista.removeIf(r -> r.getNombreSolicitante().equalsIgnoreCase(nombre))) {
      guardarListaReservas(lista);
      System.out.println("Reserva de " + nombre + " eliminada del XML.");
    } else {
      System.out.println("No se encontró la reserva para eliminar.");
    }
  }
}
