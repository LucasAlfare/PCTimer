package bomesmo.main.core;

import bomesmo.main.auxiliar.TF;
import bomesmo.main.core.statistics.*;

import javax.swing.*;
import java.util.ArrayList;

//Statistics List Refresher
public class SLR {

    public static void refresh(JList<String> lista, ArrayList<Solve> solves){
        lista.setModel(new AbstractListModel<String>() {
            String[] x = elements(solves);

            @Override
            public int getSize() {
                return x.length;
            }

            @Override
            public String getElementAt(int index) {
                return x[index];
            }
        });
    }

    private static String[] elements(ArrayList<Solve> solves){
        Statistic best = new Best(solves);
        Statistic worst = new Worst(solves);

        Statistic overrMean = new OverrMean(solves);
        Statistic overrAvg = new OverrAverage(solves);

        Statistic best5 = new BestAvgX(solves, 5);
        Statistic current5 = new CurrentAvgX(solves, 5);
        Statistic worst5 = new WorstAvgX(solves, 5);

        Statistic best12 = new BestAvgX(solves, 12);
        Statistic current12 = new CurrentAvgX(solves, 12);
        Statistic worst12 = new WorstAvgX(solves, 12);

        Statistic best50 = new BestAvgX(solves, 50);
        Statistic current50 = new CurrentAvgX(solves, 50);
        Statistic worst50 = new WorstAvgX(solves, 50);

        Statistic best100 = new BestAvgX(solves, 100);
        Statistic current100 = new CurrentAvgX(solves, 100);
        Statistic worst100 = new WorstAvgX(solves, 100);

        return new String[]{
                "melhor tempo: " + (best.result() > 0 ? TF.format(best.result()) : "- -"),
                "pior tempo: " + (worst.result() > 0 ? TF.format(worst.result()) : "- -"),

                "/----------------------/",

                "média geral: " + (overrMean.result() > 0 ? TF.format(overrMean.result()) : "- -"),
                "average geral: " + (overrAvg.result() > 0 ? TF.format(overrAvg.result()) : "- -"),

                "/----------------------/",
                "melhor avg5: " + (best5.result() > 0 ? TF.format(best5.result()) : "- -"),
                "current avg5: " + (current5.result() > 0 ? TF.format(current5.result()) : "- -"),
                "pior avg5: " + (worst5.result() > 0 ? TF.format(worst5.result()) : "- -"),
                "/----------------------/",

                "melhor avg12: " + (best12.result() > 0 ? TF.format(best12.result()) : "- -"),
                "current avg12: " + (current12.result() > 0 ? TF.format(current12.result()) : "- -"),
                "pior avg12: " + (worst12.result() > 0 ? TF.format(worst12.result()) : "- -"),
                "/----------------------/",

                "melhor avg50: " + (best50.result() > 0 ? TF.format(best50.result()) : "- -"),
                "current avg50: " + (current50.result() > 0 ? TF.format(current50.result()) : "- -"),
                "pior avg50: " + (worst50.result() > 0 ? TF.format(worst50.result()) : "- -"),
                "/----------------------/",

                "melhor avg100: " + (best100.result() > 0 ? TF.format(best100.result()) : "- -"),
                "current avg100: " + (current100.result() > 0 ? TF.format(current100.result()) : "- -"),
                "pior avg100: " + (worst100.result() > 0 ? TF.format(worst100.result()) : "- -")
        };
    }
}