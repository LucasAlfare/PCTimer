package bomesmo.timer.main.core.statistics;

import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;
import java.util.Collections;

public class Best extends Statistic{

    public Best(ArrayList<Solve> solves) {
        super(solves);
    }

    @Override
    public long result() {
        ArrayList<Long> times = Misc.getTimes(solves);

        return !solves.isEmpty() ? Collections.min(times) : 0;
    }

    @Override
    public String details() {
        return !solves.isEmpty() ? solves.get(Misc.indexOfBest(solves)).toString() : "";
    }
}