package bomesmo.timer.main.core.statistics;

import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.auxiliar.TF;
import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;

public class CurrentAvgX extends Statistic {

    private int averageLength;

    public CurrentAvgX(ArrayList<Solve> solves, int averageLength) {
        super(solves);
        this.averageLength = averageLength;
    }

    @Override
    public long result() {
        return solves.size() >= averageLength ?
                Misc.averageFrom(Misc.lastAverage(solves, averageLength)) : 0;
    }

    @Override
    public String details() {
        StringBuilder stringBuilder = new StringBuilder();

        if (solves.size() >= averageLength){
            Solve[] result = Misc.lastAverage(solves, averageLength);

            //loop reverso pois obtenho as solves de forma reversa, ai assim fica em ordem
            for (int i = result.length; i > 0; i--){
                stringBuilder.append(String.valueOf((result.length - i) + 1));
                stringBuilder.append(". ");

                stringBuilder.append(
                        Misc.isBest
                                (result[i - 1], result) ||
                                Misc.isWorst(result[i - 1], result) ?
                                "(" + TF.format(result[i - 1].getTempo()) + ") " + result[i - 1].getScramble() + ";" :
                                result[i - 1]);

                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    public int getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(int averageLength) {
        this.averageLength = averageLength;
    }
}