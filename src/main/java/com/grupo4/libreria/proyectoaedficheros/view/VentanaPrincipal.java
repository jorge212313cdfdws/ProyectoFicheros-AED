package com.grupo4.libreria.proyectoaedficheros.view;

import com.grupo4.libreria.proyectoaedficheros.dao.ReservaLibroDAOCsv;
import com.grupo4.libreria.proyectoaedficheros.dao.ReservaLibroDAOJson;
import com.grupo4.libreria.proyectoaedficheros.dao.ReservaLibroDAOObj;
import com.grupo4.libreria.proyectoaedficheros.dao.ReservaLibroDAOXml;
import com.grupo4.libreria.proyectoaedficheros.model.GestorReservas;
import com.grupo4.libreria.proyectoaedficheros.model.ReservaLibro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private GestorReservas gestor;
    private JTable tablaReservas;
    private DefaultTableModel tableModel;

    private ReservaLibroDAOJson daoJson;
    private ReservaLibroDAOXml daoXml;
    private ReservaLibroDAOCsv daoCsv;
    private ReservaLibroDAOObj daoObj;

    private final Color COLOR_FONDO = new Color(245, 245, 250);
    private final Color COLOR_PRIMARIO = new Color(60, 130, 200);
    private final Color COLOR_TEXTO_BLANCO = Color.WHITE;

    public VentanaPrincipal() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        gestor = new GestorReservas();

        daoJson = new ReservaLibroDAOJson();
        daoXml = new ReservaLibroDAOXml();
        daoCsv = new ReservaLibroDAOCsv();
        daoObj = new ReservaLibroDAOObj();

        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Reservas de Biblioteca");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        JLabel lblTitulo = new JLabel("Listado de Reservas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnNames = {"Título del Libro", "Solicitante", "Fecha Reserva", "Fecha Devolución"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReservas = new JTable(tableModel);
        estilizarTabla(tablaReservas);

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(COLOR_FONDO);

        JButton btnAnadir = crearBoton("Añadir Reserva");
        JButton btnEditar = crearBoton("Editar");
        JButton btnEliminar = crearBoton("Eliminar");
        btnEliminar.setBackground(new Color(220, 80, 80));

        btnAnadir.addActionListener(e -> mostrarDialogoReserva(null));
        btnEditar.addActionListener(e -> editarReservaSeleccionada());
        btnEliminar.addActionListener(e -> eliminarReservaSeleccionada());

        panelBotones.add(btnAnadir);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        crearMenu();
    }

    private void estilizarTabla(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(200, 220, 240));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(COLOR_TEXTO_BLANCO);
        header.setOpaque(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(COLOR_TEXTO_BLANCO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 100, 180), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        return btn;
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenu menuCargar = new JMenu("Cargar de...");
        JMenuItem itemCargarJson = new JMenuItem("JSON");
        JMenuItem itemCargarXml = new JMenuItem("XML");
        JMenuItem itemCargarCsv = new JMenuItem("CSV");
        JMenuItem itemCargarObj = new JMenuItem("Fichero Objetos (.bin)");

        itemCargarJson.addActionListener(e -> cargarDatos("JSON"));
        itemCargarXml.addActionListener(e -> cargarDatos("XML"));
        itemCargarCsv.addActionListener(e -> cargarDatos("CSV"));
        itemCargarObj.addActionListener(e -> cargarDatos("OBJ"));

        menuCargar.add(itemCargarJson);
        menuCargar.add(itemCargarXml);
        menuCargar.add(itemCargarCsv);
        menuCargar.add(itemCargarObj);

        JMenu menuGuardar = new JMenu("Guardar en...");
        JMenuItem itemGuardarJson = new JMenuItem("JSON");
        JMenuItem itemGuardarXml = new JMenuItem("XML");
        JMenuItem itemGuardarCsv = new JMenuItem("CSV");
        JMenuItem itemGuardarObj = new JMenuItem("Fichero Objetos (.bin)");

        itemGuardarJson.addActionListener(e -> guardarDatos("JSON"));
        itemGuardarXml.addActionListener(e -> guardarDatos("XML"));
        itemGuardarCsv.addActionListener(e -> guardarDatos("CSV"));
        itemGuardarObj.addActionListener(e -> guardarDatos("OBJ"));

        menuGuardar.add(itemGuardarJson);
        menuGuardar.add(itemGuardarXml);
        menuGuardar.add(itemGuardarCsv);
        menuGuardar.add(itemGuardarObj);

        menuArchivo.add(menuCargar);
        menuArchivo.add(menuGuardar);
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
            JOptionPane.showMessageDialog(this, "Selecciona una reserva para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarReservaSeleccionada() {
        int row = tablaReservas.getSelectedRow();
        if (row >= 0) {
            ReservaLibro reserva = gestor.getReservas().get(row);
            mostrarDialogoReserva(reserva);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una reserva para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarDialogoReserva(ReservaLibro reservaExistente) {
        JDialog dialog = new JDialog(this, reservaExistente == null ? "Nueva Reserva" : "Editar Reserva", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtTitulo = new JTextField(reservaExistente != null ? reservaExistente.getTituloLibro() : "");
        JTextField txtSolicitante = new JTextField(reservaExistente != null ? reservaExistente.getNombreSolicitante() : "", 20);
        JTextField txtFechaReserva = new JTextField(reservaExistente != null ? reservaExistente.getFechaReserva().toString() : LocalDate.now().toString());
        JTextField txtFechaDevolucion = new JTextField(reservaExistente != null && reservaExistente.getFechaDevolucion() != null ? reservaExistente.getFechaDevolucion().toString() : "");

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Título del Libro:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Solicitante:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtSolicitante, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Fecha Reserva:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtFechaReserva, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Fecha Devolución:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtFechaDevolucion, gbc);

        JButton btnGuardar = crearBoton("Guardar");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelForm.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String solicitante = txtSolicitante.getText();

                LocalDate fechaRes = LocalDate.parse(txtFechaReserva.getText().trim());

                LocalDate fechaDev = null;
                String textoFechaDev = txtFechaDevolucion.getText().trim();
                if (!textoFechaDev.isEmpty()) {
                    fechaDev = LocalDate.parse(textoFechaDev);
                }

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

        dialog.add(panelForm);
        dialog.setVisible(true);
    }

    private void guardarDatos(String tipo) {
        try {
            List<ReservaLibro> lista = gestor.getReservas();
            switch (tipo) {
                case "JSON": daoJson.guardar(lista); break;
                case "XML": daoXml.guardar(lista); break;
                case "CSV": daoCsv.guardar(lista); break;
                case "OBJ": daoObj.guardar(lista); break;
            }
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente en " + tipo + ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos(String tipo) {
        try {
            List<ReservaLibro> lista = null;
            switch (tipo) {
                case "JSON": lista = daoJson.cargar(); break;
                case "XML": lista = daoXml.cargar(); break;
                case "CSV": lista = daoCsv.cargar(); break;
                case "OBJ": lista = daoObj.cargar(); break;
            }

            if (lista != null) {
                gestor.getReservas().clear();
                gestor.getReservas().addAll(lista);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Datos cargados correctamente desde " + tipo + ".");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
