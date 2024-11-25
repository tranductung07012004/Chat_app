package org.example;

import org.example.GUI.*;

public class Main {
    public static void main(String[] args) {
        // Cài đặt Nimbus Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Khởi tạo và hiển thị MainFrameGUI
        java.awt.EventQueue.invokeLater(() -> new MainFrameGUI());
    }
}







