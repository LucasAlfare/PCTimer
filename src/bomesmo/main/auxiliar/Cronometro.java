package bomesmo.main.auxiliar;

import javax.swing.*;
import java.util.TimerTask;

public class Cronometro extends TimerTask {

    private long i = System.currentTimeMillis();
    public long e;
    private JLabel target;

    public Cronometro(JLabel target){
        this.target = target;
    }

    private long elapsedTime(){
        e = System.currentTimeMillis() - i;
        return e;
    }

    @Override
    public void run() {
        target.setText(TF.format(elapsedTime()));
    }
}