package com.mycompany.gestionautores.main;

import com.mycompany.gestionautores.vista.FormAutores;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Principal {
    public static void main(String[] args) {
        try {
            // Intentar usar el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            FormAutores form = new FormAutores();
            form.setVisible(true);
        });
    }
}