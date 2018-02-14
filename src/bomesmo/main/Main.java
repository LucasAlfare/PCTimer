package bomesmo.main;

import bomesmo.main.core.Core;
import bomesmo.main.core.gui.Gui;
import com.cs.main.puzzle.Table;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Table>  shapesPreferidos = new ArrayList<>();

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Core(new Gui()));
    }
}