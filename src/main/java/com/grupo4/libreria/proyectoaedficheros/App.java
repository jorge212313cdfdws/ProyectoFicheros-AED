package com.grupo4.libreria.proyectoaedficheros;

import com.grupo4.libreria.proyectoaedficheros.view.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
