package bomesmo.main.core.scramblers;

public class PocketScrambler extends Scramble{

    @Override
    public String getSequence() {

        String last = "", curr = "";

        StringBuilder r = new StringBuilder();

        for (int i = 0; i < 10; i++){
            do {
                curr = pocketMoves[rnd.nextInt(pocketMoves.length)];
            } while (curr.equals(last));

            last = curr;

            r.append(curr).append(normalDirections[rnd.nextInt(normalDirections.length)]);
        }

        return r.toString();
    }
}