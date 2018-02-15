package bomesmo.timer.main.core.statistics;

import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;

public class BestAvgX extends Statistic {

    private int averageLength;

    public BestAvgX(ArrayList<Solve> solves, int averageLength) {
        super(solves);
        this.averageLength = averageLength;
    }

    @Override
    public long result() {
        return solves.size() < averageLength ?
                0 : Misc.averageFrom(Misc.averageSolves(solves, averageLength));
    }

    @Override
    public String details() {
        String r = "";

        if (solves.size() >= averageLength){
            Solve[] result = Misc.averageSolves(solves, averageLength);

            r = Misc.statisticDetails(result);
        }

        return r;
    }

    public int getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(int averageLength) {
        this.averageLength = averageLength;
    }
}