package bomesmo.timer.main.core.statistics;

import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;

public class OverrAverage extends Statistic {

    public OverrAverage(ArrayList<Solve> solves) {
        super(solves);
    }

    @Override
    public long result() {
        return solves.size() >= 3 ? Misc.averageFrom(solves) : 0;
    }

    @Override
    public String details() {
        String r = "";

        if (solves.size() >= 3){
            ArrayList<Solve> result = new ArrayList<>();

            for (int i = 0; i < solves.size(); i++){
                if (!Misc.isBest(solves.get(i), solves.toArray(new Solve[solves.size()])) ||
                        Misc.isWorst(solves.get(i), solves.toArray(new Solve[solves.size()]))){

                    result.add(solves.get(i));
                }
            }

            r = Misc.statisticDetails(result.toArray(new Solve[result.size()]));
        }

        return r;
    }
}