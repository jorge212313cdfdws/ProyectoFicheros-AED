package com.grupo4.libreria.proyectoaedficheros.view;

import com.grupo4.libreria.proyectoaedficheros.dao.ReservaLibroDAOJson;
import com.grupo4.libreria.proyectoaedficheros.model.GestorReservas;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private GestorReservas gestor;
    private JTable tablaReservas;
    private DefaultTableModel tableModel;
    private ReservaLibroDAOJson daoJson;

    public VentanaPrincipal() {
        gestor = new GestorReservas();
        daoJson = new ReservaLibroDAOJson();
        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Reservas de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TABLA ---
        String[] columnNames = {"Título", "Solicitante", "Fecha Reserva", "Fecha Devolución"};
        // Hacemos que las celdas no sean editables directamente
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReservas = new JTable(tableModel);
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaReservas), BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel panelBotones = new JPanel();
        JButton btnAnadir = new JButton("Añadir");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAnadir.addActionListener(e -> mostrarDialogoReserva(null));
        btnEditar.addActionListener(e -> editarReservaSeleccionada());
        btnEliminar.addActionListener(e -> eliminarReservaSeleccionada());

        panelBotones.add(btnAnadir);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // --- MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        
        JMenuItem itemGuardarJson = new JMenuItem("Guardar (JSON)");
        JMenuItem itemCargarJson = new JMenuItem("Cargar (JSON)");

        itemGuardarJson.addActionListener(e -> guardarDatosJson());
        itemCargarJson.addActionListener(e -> cargarDatosJson());

        menuArchivo.add(itemGuardarJson);
        menuArchivo.add(itemCargarJson);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (ReservaLibro r : gestor.getReservas()) {
            tableModel.addRow(new Object[]{
                    r.getTituloLibro(),
                    r.getNombreSolicitante(),
                    r.getFechaReserva(),
                    r.getFechaDevolucion()
            });
        }
    }

    private void eliminarReservaSeleccionada() {
        int row = tablaReservas.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Estás seguro de eliminar la reserva seleccionada?", 
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                gestor.eliminarReserva(row);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una reserva para eliminar.");
        }
    }

    private void editarReservaSeleccionada() {
        int row = tablaReservas.getSelectedRow();
        if (row >= 0) {
            ReservaLibro reserva = gestor.getReservas().get(row);
            mostrarDialogoReserva(reserva);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una reserva para editar.");
        }
    }

    private void mostrarDialogoReserva(ReservaLibro reservaExistente) {
        JDialog dialog = new JDialog(this, reservaExistente == null ? "Nueva Reserva" : "Editar Reserva", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JTextField txtTitulo = new JTextField(reservaExistente != null ? reservaExistente.getTituloLibro() : "");
        JTextField txtSolicitante = new JTextField(reservaExistente != null ? reservaExistente.getNombreSolicitante() : "");
        JTextField txtFechaReserva = new JTextField(reservaExistente != null ? reservaExistente.getFechaReserva().toString() : LocalDate.now().toString());
        JTextField txtFechaDevolucion = new JTextField(reservaExistente != null ? reservaExistente.getFechaDevolucion().toString() : LocalDate.now().plusDays(7).toString());

        dialog.add(new JLabel("  Título del Libro:"));
        dialog.add(txtTitulo);
        dialog.add(new JLabel("  Solicitante:"));
        dialog.add(txtSolicitante);
        dialog.add(new JLabel("  Fecha Reserva (YYYY-MM-DD):"));
        dialog.add(txtFechaReserva);
        dialog.add(new JLabel("  Fecha Devolución (YYYY-MM-DD):"));
        dialog.add(txtFechaDevolucion);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String solicitante = txtSolicitante.getText();
                LocalDate fechaRes = LocalDate.parse(txtFechaReserva.getText());
                LocalDate fechaDev = LocalDate.parse(txtFechaDevolucion.getText());

                ReservaLibro nuevaReserva = new ReservaLibro(titulo, solicitante, fechaRes, fechaDev);

                if (reservaExistente == null) {
                    gestor.añadirReserva(nuevaReserva);
                } else {
                    int index = gestor.getReservas().indexOf(reservaExistente);
                    gestor.modificarReserva(index, nuevaReserva);
                }
                actualizarTabla();
                dialog.dispose();
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(dialog, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(dialog, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel("")); // Spacer
        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    private void guardarDatosJson() {
        try {
            daoJson.guardar(gestor.getReservas());
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente en JSON.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosJson() {
        try {
            List<ReservaLibro> lista = daoJson.cargar();
            gestor.getReservas().clear();
            gestor.getReservas().addAll(lista);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Datos cargados correctamente desde JSON.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
