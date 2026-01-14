package com.grupo4.libreria.proyectoaedficheros.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la gestión de reservas de libros.
 * Permite crear, modificar y eliminar reservas.
 */
public class GestorReservas {

    private final List<ReservaLibro> reservas;

    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    /**
     * Devuelve la lista de reservas.
     */
    public List<ReservaLibro> getReservas() {
        return reservas;
    }

    /**
     * Añade una nueva reserva tras validarla.
     */
    public void añadirReserva(ReservaLibro reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }
        reserva.validar();
        reservas.add(reserva);
    }

    /**
     * Elimina una reserva según su índice.
     */
    public void eliminarReserva(int indice) {
        comprobarIndice(indice);
        reservas.remove(indice);
    }

    /**
     * Modifica una reserva existente.
     */
    public void modificarReserva(int indice, ReservaLibro nuevaReserva) {
        comprobarIndice(indice);

        if (nuevaReserva == null) {
            throw new IllegalArgumentException("La nueva reserva no puede ser nula");
        }

        nuevaReserva.validar();
        reservas.set(indice, nuevaReserva);
    }

    /**
     * Comprueba que el índice sea válido.
     */
    private void comprobarIndice(int indice) {
        if (indice < 0 || indice >= reservas.size()) {
            throw new IndexOutOfBoundsException("Índice de reserva no válido");
        }
    }

    /**
     * Indica si no hay reservas registradas.
     */
    public boolean estaVacio() {
        return reservas.isEmpty();
    }
}
