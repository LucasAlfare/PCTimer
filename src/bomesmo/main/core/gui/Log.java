package bomesmo.main.core.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Log extends JFrame {

    private String texto;
    private JTextArea jTextArea;

    public Log(String texto){
        this.texto = texto;

        setSize(400, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Solve selecionada");

        jTextArea = new JTextArea();
        jTextArea.setFont(new java.awt.Font("Noto Sans", 3, 24)); // NOI18N
        jTextArea.setText(this.texto);

        add(jTextArea);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    dispose();
                }
            }
        });
    }
}