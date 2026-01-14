package com.grupo4.libreria.proyectoaedficheros;

import com.grupo4.libreria.proyectoaedficheros.model.GestorReservas;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {

        System.out.println("=== PRUEBAS PUNTO 1: MODELO Y GESTIÓN DE RESERVAS ===");

        GestorReservas gestor = new GestorReservas();

        // 1️⃣ Crear reservas válidas
        ReservaLibro r1 = new ReservaLibro(
                "El Quijote",
                "Ana López",
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        ReservaLibro r2 = new ReservaLibro(
                "1984",
                "Carlos Pérez",
                LocalDate.now(),
                LocalDate.now().plusDays(10)
        );

        // 2️⃣ Añadir reservas
        gestor.añadirReserva(r1);
        gestor.añadirReserva(r2);

        System.out.println("\nReservas tras añadir:");
        gestor.getReservas().forEach(System.out::println);

        // 3️⃣ Modificar una reserva
        ReservaLibro rModificada = new ReservaLibro(
                "1984 (Edición especial)",
                "Carlos Pérez",
                LocalDate.now(),
                LocalDate.now().plusDays(15)
        );

        gestor.modificarReserva(1, rModificada);

        System.out.println("\nReservas tras modificar la segunda:");
        gestor.getReservas().forEach(System.out::println);

        // 4️⃣ Eliminar una reserva
        gestor.eliminarReserva(0);

        System.out.println("\nReservas tras eliminar la primera:");
        gestor.getReservas().forEach(System.out::println);

        // 5️⃣ Prueba de validación (debe FALLAR)
        try {
            ReservaLibro reservaInvalida = new ReservaLibro(
                    "Libro inválido",
                    "Pepe",
                    LocalDate.now(),
                    LocalDate.now().minusDays(3)
            );

            gestor.añadirReserva(reservaInvalida);

        } catch (IllegalArgumentException e) {
            System.out.println("\nValidación correcta detectada:");
            System.out.println(e.getMessage());
        }

        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}

