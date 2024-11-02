package com.mycompany.gestionautores.vista;

import com.mycompany.gestionautores.dao.AutorDAO;
import com.mycompany.gestionautores.dao.AutorDAOImpl;
import com.mycompany.gestionautores.modelo.Autor;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;

public class DialogoAutor extends JDialog {
    private JTextField txtNombre, txtPais, txtPublicaciones;
    private JTextField txtFechaNacimiento, txtFechaPrimeraPublicacion;
    private JButton btnGuardar, btnCancelar;
    private AutorDAO autorDAO;
    private Autor autorEditar;
    private boolean aceptado = false;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public DialogoAutor(JFrame parent, Autor autor) {
        super(parent, true);
        this.autorDAO = new AutorDAOImpl();
        this.autorEditar = autor;
        inicializarComponentes();
        if (autor != null) {
            cargarDatosAutor();
        }
    }
    
    private void inicializarComponentes() {
        setTitle(autorEditar == null ? "Nuevo Autor" : "Editar Autor");
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        
        // Panel principal con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos de texto
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        txtPais = new JTextField(20);
        panel.add(txtPais, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Publicaciones:"), gbc);
        gbc.gridx = 1;
        txtPublicaciones = new JTextField(20);
        panel.add(txtPublicaciones, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtFechaNacimiento = new JTextField(20);
        panel.add(txtFechaNacimiento, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Primera Publicación (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtFechaPrimeraPublicacion = new JTextField(20);
        panel.add(txtFechaPrimeraPublicacion, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancellar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Agregar paneles a la ventana
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void cargarDatosAutor() {
        txtNombre.setText(autorEditar.getNombre());
        txtPais.setText(autorEditar.getPais());
        txtPublicaciones.setText(String.valueOf(autorEditar.getPublicaciones()));
        txtFechaNacimiento.setText(autorEditar.getFechaNacimiento().format(FORMATO_FECHA));
        txtFechaPrimeraPublicacion.setText(autorEditar.getFechaPrimeraPublicacion().format(FORMATO_FECHA));
    }
    
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio");
            return false;
        }
        if (txtPais.getText().trim().isEmpty()) {
            mostrarError("El país es obligatorio");
            return false;
        }
        try {
            int publicaciones = Integer.parseInt(txtPublicaciones.getText().trim());
            if (publicaciones < 0) {
                mostrarError("El número de publicaciones debe ser positivo");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El número de publicaciones debe ser un número válido");
            return false;
        }
        try {
            LocalDate fechaNac = LocalDate.parse(txtFechaNacimiento.getText().trim(), FORMATO_FECHA);
            LocalDate fechaPub = LocalDate.parse(txtFechaPrimeraPublicacion.getText().trim(), FORMATO_FECHA);
            if (fechaPub.isBefore(fechaNac)) {
                mostrarError("La fecha de primera publicación no puede ser anterior a la fecha de nacimiento");
                return false;
            }
        } catch (DateTimeParseException e) {
            mostrarError("Las fechas deben tener el formato YYYY-MM-DD");
            return false;
        }
        return true;
    }
    
    private void guardar() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            Autor autor = new Autor(
                txtNombre.getText().trim(),
                txtPais.getText().trim(),
                Integer.parseInt(txtPublicaciones.getText().trim()),
                LocalDate.parse(txtFechaNacimiento.getText().trim(), FORMATO_FECHA),
                LocalDate.parse(txtFechaPrimeraPublicacion.getText().trim(), FORMATO_FECHA)
            );
            
            if (autorEditar != null) {
                autor.setIdAutor(autorEditar.getIdAutor());
                autorDAO.actualizar(autor);
                JOptionPane.showMessageDialog(this,
                    "Autor actualizado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id = autorDAO.insertar(autor);
                JOptionPane.showMessageDialog(this,
                    "Autor creado con ID: " + id,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            aceptado = true;
            dispose();
            
        } catch (SQLException ex) {
            mostrarError("Error al guardar el autor: " + ex.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    public boolean isAceptado() {
        return aceptado;
    }
}