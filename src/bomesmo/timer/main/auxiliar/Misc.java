package bomesmo.timer.main.auxiliar;

import bomesmo.timer.main.core.Solve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Misc {

    private static final String STATISTIC_HEADER =
            "*GERADO PELO TIMER Q EU FIZ KKKKKKK aff*\n\n";

    private static ArrayList<Long> getTimes(Solve[] solves){
        ArrayList<Long> times = new ArrayList<>();

        for (Solve x : solves){
            times.add(x.getTempo());
        }

        return times;
    }

    public static ArrayList<Long> getTimes(ArrayList<Solve> solves){
        ArrayList<Long> times = new ArrayList<>();

        for (Solve x : solves){
            times.add(x.getTempo());
        }

        return times;
    }

    private static ArrayList<Long> getTimes(List<Solve> solves){
        ArrayList<Long> times = new ArrayList<>();

        for (Solve x : solves){
            times.add(x.getTempo());
        }

        return times;
    }

    public static boolean isBest(Solve x, Solve[] source){
        long search = Long.MAX_VALUE;

        for (Solve aSource : source) {
            if (aSource.getTempo() < search) {
                search = aSource.getTempo();
            }
        }

        return x.getTempo() == search;
    }

    public static boolean isWorst(Solve x, Solve[] source){
        long search = Long.MIN_VALUE;

        for (Solve aSource : source) {
            if (aSource.getTempo() > search) {
                search = aSource.getTempo();
            }
        }

        return x.getTempo() == search;
    }

    public static int indexOfWorst(ArrayList<Solve> solves){
        ArrayList<Long> times = getTimes(solves);

        return times.indexOf(Collections.max(getTimes(solves)));
    }

    public static int indexOfBest(ArrayList<Solve> solves){
        ArrayList<Long> times = getTimes(solves);

        return times.indexOf(Collections.min(getTimes(solves)));
    }

    public static long averageFrom(ArrayList<Solve> times){
        ArrayList<Long> list = new ArrayList<>();
        list.addAll(getTimes(times));

        list.remove(Collections.max(list));
        list.remove(Collections.min(list));

        long sum = 0;

        for (long x : list) sum += x;

        return sum / list.size();
    }

    public static long averageFrom(Solve[] times){
        ArrayList<Long> list = getTimes(times);

        list.remove(Collections.max(list));
        list.remove(Collections.min(list));

        long sum = 0;

        for (long x : list) sum += x;

        return sum / list.size();
    }

    private static long averageFrom(List<Solve> times){
        List<Long> list = new ArrayList<>();
        list.addAll(getTimes(times));

        list.remove(Collections.max(list));
        list.remove(Collections.min(list));

        long sum = 0;

        for (long x : list) sum += x;

        return sum / list.size();
    }

    private static Solve[] solvesListToArray(List<Solve> source){
        Solve[] r = new Solve[source.size()];

        for (int i = 0; i < source.size(); i++){
            r[i] = source.get(i);
        }

        return r;
    }

    public static Solve[] averageSolves(ArrayList<Solve> source, int power) {
        return averageSolves(source, power, true);
    }

    public static Solve[] averageSolves(ArrayList<Solve> source, int power, boolean best){
        long search = best ? Long.MAX_VALUE : Long.MIN_VALUE;
        List<Solve> aux;
        Solve[] r = new Solve[power];

        for (int i = 0; i < source.size(); i++){

            try {
                aux = source.subList(i, i+(power));

                if (best){
                    if (Misc.averageFrom(aux) < search){
                        search = Misc.averageFrom(aux);
                        r = Misc.solvesListToArray(aux);
                    }
                } else {
                    if (Misc.averageFrom(aux) > search){
                        search = Misc.averageFrom(aux);
                        r = Misc.solvesListToArray(aux);
                    }
                }

            } catch (Exception e){/*pass*/}
        }

        return r;
    }

    public static Solve[] lastAverage(ArrayList<Solve> source, int power){
        ArrayList<Solve> aux = new ArrayList<>();

        if (source.size() >= power){

            for (int i = source.size() - 1; i > (source.size() - power) - 1; i--){
                aux.add(source.get(i));
            }
        }

        return aux.toArray(new Solve[aux.size()]);
    }

    public static String statisticDetails(Solve[] result){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(STATISTIC_HEADER);

        for (int i = 0; i < result.length; i++){
            stringBuilder.append(String.valueOf(i + 1));
            stringBuilder.append(". ");

            stringBuilder.append(
                    Misc.isBest
                            (result[i], result) ||
                            Misc.isWorst(result[i], result) ?
                            "(" + TF.format(result[i].getTempo()) + ") " + result[i].getScramble() + ";" :
                            result[i]);

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}