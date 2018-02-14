package bomesmo.main.core.scramblers;

public class RubiksCubeScrambler extends Scramble {

    @Override
    public String getSequence() {
        StringBuilder r = new StringBuilder();
        String a = "  ", b = "  ", c = "  ";

        for (int i = 0; i < 24; i++){
            do {
                c = rubiksMoves[rnd.nextInt(rubiksMoves.length)];
            } while (c.equals(b) || sameAxis(a, b, c));

            a = b;
            b = c;

            r.append(c.charAt(0))
                    .append(normalDirections[rnd.nextInt(normalDirections.length)]);
        }


        return r.toString();
    }

    private boolean sameAxis(String x, String y, String z){
        return x.charAt(1) == y.charAt(1) && x.charAt(1) == z.charAt(1);
    }
}