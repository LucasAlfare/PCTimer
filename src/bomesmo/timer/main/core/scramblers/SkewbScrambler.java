package bomesmo.timer.main.core.scramblers;

public class SkewbScrambler extends Scramble{

    @Override
    public String getSequence() {

        String last = "", curr = "";

        StringBuilder r = new StringBuilder();

        for (int i = 0; i < 10; i++){
            do {
                curr = skewbMoves[rnd.nextInt(skewbMoves.length)];
            } while (curr.equals(last));

            last = curr;

            r.append(curr).append(skewbDirections[rnd.nextInt(skewbDirections.length)]);
        }

        return r.toString();
    }
}