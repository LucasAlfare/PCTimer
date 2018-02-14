package bomesmo.main.core.statistics;

import bomesmo.main.auxiliar.Misc;
import bomesmo.main.core.Solve;

import java.util.ArrayList;
import java.util.Collections;

public class Worst extends Statistic {

    public Worst(ArrayList<Solve> solves) {
        super(solves);
    }

    @Override
    public long result() {
        ArrayList<Long> times = Misc.getTimes(solves);

        return !solves.isEmpty() ? Collections.max(times) : 0;
    }

    @Override
    public String details() {
        return !solves.isEmpty() ? solves.get(Misc.indexOfWorst(solves)).toString() : "";
    }
}