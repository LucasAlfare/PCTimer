package bomesmo.main.core;

import bomesmo.main.auxiliar.Cronometro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

//Space Bar Listener
public class SBL implements KeyListener {

    private Core core;
    private JComponent[] components;

    private Timer timerLong, timer;
    private Cronometro cronometro;

    private String scrambleMostrado;

    private boolean isLong, started;

    public SBL(Core core, JComponent... components) {
        this.core = core;
        this.components = components;
    }

    private void start(){
        timer = new Timer();
        cronometro = new Cronometro(core.getGui().getLblTimer());
        timer.scheduleAtFixedRate(cronometro, 0, 51);
        started = true;
    }

    private void stop(){
        timer.cancel();
        started = false;

        TLR.refresh(core.getGui().getListaTempos(), core.getSolves());
        SLR.refresh(core.getGui().getListaEstatisticas(), core.getSolves());

        /*
          Thread para prevenir que o programa trave enquanto gera um scramble (ao parar)
          Thread to prevent program stuck when generating scramble (onStop)
         */
        new Thread(() -> {
            core.getGui().getLblScramble().setText("carregando....");//first
            core.getGui().getLblScramble().setText(core.getScramble().getSequence());//sucess

            java.awt.EventQueue.invokeLater(() -> {
                core.getGui().setState(Frame.NORMAL);
                core.getGui().toFront();
                core.getGui().repaint();
            });
        }).start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!core.getGui().getLblScramble().getText().contains("carregando")){
            if (e.getKeyCode() == 32){

                //core.getGui().getLblTimer().setFont(new java.awt.Font("Noto Sans", 3, 60)); // NOI18N
                core.getGui().getLblTimer().setFont(new java.awt.Font("Impact", 1, 80)); // NOI18N
                scrambleMostrado = core.getGui().getLblScramble().getText();

                if (timerLong == null){
                    timerLong = new Timer();

                    timerLong.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            isLong = true;
                        }
                    }, 300, 1000);
                }

                for (JComponent component : components){
                    component.setEnabled(!isLong);
                }

                if (started) {
                    core.getSolves().add(new Solve(cronometro.e, scrambleMostrado));
                    stop();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!core.getGui().getLblScramble().getText().contains("carregando")){
            if (e.getKeyCode() == 32){

                //core.getGui().getLblTimer().setFont(new java.awt.Font("Noto Sans", 3, 75)); // NOI18N
                core.getGui().getLblTimer().setFont(new java.awt.Font("Impact", 1, 100)); // NOI18N

                if (isLong) start();

                isLong = false;
                timerLong.cancel();
                timerLong = null;
            }
        }
    }
}