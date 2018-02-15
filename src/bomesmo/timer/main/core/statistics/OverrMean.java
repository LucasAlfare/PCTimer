package bomesmo.timer.main.core.statistics;

import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;

public class OverrMean extends Statistic {

    public OverrMean(ArrayList<Solve> solves) {
        super(solves);
    }

    @Override
    public long result() {
        long sum = 0;

        for (Solve x : solves){
            sum += x.getTempo();
        }

        return sum / solves.size();
    }

    @Override
    public String details() {
        String r = "";

        if (solves.size() >= 2){
            r = Misc.statisticDetails(solves.toArray(new Solve[solves.size()]));
        }

        return r;
    }
}