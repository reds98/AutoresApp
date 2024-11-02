package com.mycompany.gestionautores.vista;

import com.mycompany.gestionautores.dao.AutorDAO;
import com.mycompany.gestionautores.dao.AutorDAOImpl;
import com.mycompany.gestionautores.modelo.Autor;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormAutores extends JFrame {
    private JTable tablaAutores;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnEliminar, btnActualizar;
    private AutorDAO autorDAO;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public FormAutores() {
        autorDAO = new AutorDAOImpl();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Gestión de Autores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        // Crear el modelo de la tabla
        String[] columnas = {"ID", "Nombre", "País", "Publicaciones", "Fecha Nacimiento", "Primera Publicación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Configurar la tabla
        tablaAutores = new JTable(modeloTabla);
        tablaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaAutores);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnNuevo = new JButton("Nuevo Autor");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        // Agregar componentes a la ventana
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos de botones
        configurarEventos();
    }
    
    private void configurarEventos() {
        btnNuevo.addActionListener(e -> mostrarDialogoAutor(null));
        
        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaAutores.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Autor autor = obtenerAutorDeTabla(filaSeleccionada);
                mostrarDialogoAutor(autor);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, seleccione un autor para editar",
                    "Editar Autor", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaAutores.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este autor?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    eliminarAutor(filaSeleccionada);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un autor para eliminar",
                    "Eliminar Autor",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnActualizar.addActionListener(e -> cargarDatos());
    }
    
    private void cargarDatos() {
        try {
            List<Autor> autores = autorDAO.listarTodos();
            modeloTabla.setRowCount(0);
            
            for (Autor autor : autores) {
                Object[] fila = {
                    autor.getIdAutor(),
                    autor.getNombre(),
                    autor.getPais(),
                    autor.getPublicaciones(),
                    autor.getFechaNacimiento().format(FORMATO_FECHA),
                    autor.getFechaPrimeraPublicacion().format(FORMATO_FECHA)
                };
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los datos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Autor obtenerAutorDeTabla(int fila) {
        return new Autor(
            (Integer) modeloTabla.getValueAt(fila, 0),
            (String) modeloTabla.getValueAt(fila, 1),
            (String) modeloTabla.getValueAt(fila, 2),
            (Integer) modeloTabla.getValueAt(fila, 3),
            LocalDate.parse((String) modeloTabla.getValueAt(fila, 4)),
            LocalDate.parse((String) modeloTabla.getValueAt(fila, 5))
        );
    }
    
    private void eliminarAutor(int fila) {
        try {
            int idAutor = (Integer) modeloTabla.getValueAt(fila, 0);
            autorDAO.eliminar(idAutor);
            modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(this,
                "Autor eliminado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar el autor: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDialogoAutor(Autor autor) {
        DialogoAutor dialogo = new DialogoAutor(this, autor);
        dialogo.setVisible(true);
        if (dialogo.isAceptado()) {
            cargarDatos();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormAutores().setVisible(true);
        });
    }
}