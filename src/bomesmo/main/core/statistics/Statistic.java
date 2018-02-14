package bomesmo.main.core.statistics;

import bomesmo.main.core.Solve;

import java.util.ArrayList;

public abstract class Statistic {

    public ArrayList<Solve> solves;

    public Statistic(ArrayList<Solve> solves) {
        this.solves = solves;
    }

    public abstract long result();

    public abstract String details();
}