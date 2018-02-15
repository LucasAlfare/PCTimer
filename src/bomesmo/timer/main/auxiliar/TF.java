package bomesmo.timer.main.auxiliar;

import java.text.SimpleDateFormat;

public class TF {

    public static String format(long time){
        long minutes = Long.parseLong(new SimpleDateFormat("mm").format(time));

        return minutes <= 0 ? new SimpleDateFormat("ss.SSS").format(time)
                : new SimpleDateFormat("mm:ss.SSS").format(time);
    }
}