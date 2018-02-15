package bomesmo.timer.main.core;

import bomesmo.timer.main.auxiliar.TF;

public class Solve {

    private long tempo;
    private String scramble;

    public Solve(long tempo, String scramble) {
        this.tempo = tempo;
        this.scramble = scramble;
    }

    public long getTempo() {
        return tempo;
    }

    public String getScramble() {
        return scramble;
    }

    @Override
    public String toString() {
        return TF.format(tempo) + "    " + scramble + ";";
    }
}