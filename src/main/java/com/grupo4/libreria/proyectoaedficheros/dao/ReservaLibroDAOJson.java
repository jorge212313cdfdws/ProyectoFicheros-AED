package com.grupo4.libreria.proyectoaedficheros.dao;

import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ReservaLibroDAOJson {
	private static final String FICHERO_JSON = "reservas.json";

	private final Gson gson;

	public ReservaLibroDAOJson() {
		// Gson NO sabe manejar LocalDate por defecto
		gson = new GsonBuilder()
				.registerTypeAdapter(LocalDate.class,
						(com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc,
								context) -> new com.google.gson.JsonPrimitive(src.toString()))
				.registerTypeAdapter(LocalDate.class,
						(com.google.gson.JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate
								.parse(json.getAsString()))
				.setPrettyPrinting()
				.create();
	}

	public void guardar(List<ReservaLibro> reservas) throws IOException {
		try (FileWriter writer = new FileWriter(FICHERO_JSON)) {
			gson.toJson(reservas, writer);
		}
	}

	public List<ReservaLibro> cargar() throws IOException {
		Type tipoLista = new TypeToken<ArrayList<ReservaLibro>>() {
		}.getType();

		try (FileReader reader = new FileReader(FICHERO_JSON)) {
			List<ReservaLibro> reservas = gson.fromJson(reader, tipoLista);
			return reservas != null ? reservas : new ArrayList<>();
		}
	}
}
