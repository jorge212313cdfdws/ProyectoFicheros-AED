package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;

public class ReservaJsonController {

	// ! Configuración de GSON con adaptadores para LocalDate (imprescindible para
	// Java 8+)
	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class,
					(JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
			.registerTypeAdapter(LocalDate.class,
					(JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
			.setPrettyPrinting()
			.create();

	// ! Ruta del archivo JSON de reservas
	private static final String RUTA_ARCHIVO = "src/main/java/org/reservas.json";

	public static void main(String[] args) {
		// ? Espacio para pruebas de los métodos CRUD
		getAllReservas();
	}

	// * --- MÉTODOS AUXILIARES DE PERSISTENCIA --- *

	// * Método privado para centralizar la lectura y evitar duplicar código */
	private static List<ReservaJson> obtenerListaReservas() {
		try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
			Type listType = new TypeToken<List<ReservaJson>>() {
			}.getType();
			List<ReservaJson> lista = gson.fromJson(reader, listType);
			return (lista != null) ? lista : new ArrayList<>();
		} catch (IOException e) {
			// Si el archivo no existe, retornamos lista vacía para evitar errores
			return new ArrayList<>();
		}
	}

	// * Método privado para centralizar la escritura y asegurar el formato */
	private static void guardarLista(List<ReservaJson> lista) {
		try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
			gson.toJson(lista, writer);
		} catch (IOException e) {
			System.err.println("Error crítico al escribir en el JSON: " + e.getMessage());
		}
	}

	// * --- MÉTODOS PÚBLICOS (CRUD) --- *

	// * Método que imprime todas las reservas usando el toString personalizado del
	// modelo */
	public static void getAllReservas() {
		List<ReservaJson> lista = obtenerListaReservas();
		System.out.println("--- Listado de Reservas (JSON) ---");
		if (lista.isEmpty()) {
			System.out.println("No hay reservas registradas.");
		} else {
			lista.forEach(System.out::println);
		}
	}
	
	// * Método que imprime la reservas usando el nombre
	public static ReservaJson getReservaBySolicitante(String nombre) {
		List<ReservaJson> lista = obtenerListaReservas();

		for (ReservaJson r : lista) {
			if (r.getNombreSolicitante().equalsIgnoreCase(nombre)) {
				System.out.println("Reserva encontrada para: " + nombre);
				return r;
			}
		}

		System.out.println("No se encontró ninguna reserva para el solicitante: " + nombre);
		return null;
	}

	// * Añade una nueva reserva al archivo */
	public static void addReserva(ReservaJson nuevaReserva) {
		List<ReservaJson> lista = obtenerListaReservas();
		lista.add(nuevaReserva);
		guardarLista(lista);
		System.out.println("Reserva de '" + nuevaReserva.getTituloLibro() + "' guardada con éxito.");
	}

	// * Elimina una reserva buscando por el nombre del solicitante */
	public static void deleteReservaBySolicitante(String nombre) {
		List<ReservaJson> lista = obtenerListaReservas();
		if (lista.removeIf(r -> r.getNombreSolicitante().equalsIgnoreCase(nombre))) {
			guardarLista(lista);
			System.out.println("Reservas de " + nombre + " eliminadas.");
		} else {
			System.out.println("No se encontró ninguna reserva a nombre de: " + nombre);
		}
	}

	// * Actualiza una reserva existente pasando el objeto nuevo como parámetro */
	public static void updateReserva(String tituloOriginal, ReservaJson nuevosDatos) {
		List<ReservaJson> lista = obtenerListaReservas();
		boolean encontrado = false;

		for (ReservaJson r : lista) {
			if (r.getTituloLibro().equalsIgnoreCase(tituloOriginal)) {
				r.setTituloLibro(nuevosDatos.getTituloLibro());
				r.setNombreSolicitante(nuevosDatos.getNombreSolicitante());
				r.setFechaReserva(nuevosDatos.getFechaReserva());
				r.setFechaDevolucion(nuevosDatos.getFechaDevolucion());
				encontrado = true;
				break;
			}
		}

		if (encontrado) {
			guardarLista(lista);
			System.out.println("Reserva actualizada correctamente.");
		} else {
			System.out.println("No se encontró el libro: " + tituloOriginal);
		}
	}
}
