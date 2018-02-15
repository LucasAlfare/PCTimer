package bomesmo.timer.main.core.scramblers;

import java.util.ArrayList;

public class ClockScrambler extends Scramble {
    @Override
    public String getSequence() {
        StringBuilder r = new StringBuilder();
        int[] moves = {0, 1, 2, 3, 4, 5, 6};

        r.append("UR").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("DR").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("DL").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("UL").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));

        r.append("U").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("R").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("D").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("L").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));

        r.append("y2 ");

        r.append("U").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("R").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("D").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));
        r.append("L").append(moves[rnd.nextInt(moves.length)])
                .append((r.toString().charAt(r.toString().length() - 1) != '0' ? (rnd.nextBoolean() ? "+ " : "- ") : " "));

        int pinsUp = rnd.nextInt(4);
        ArrayList<String> pins = new ArrayList<>();

        for (int i = 0; i < pinsUp; i++){
            String currPin = clockPins[rnd.nextInt(clockPins.length)];

            if (!pins.contains(currPin)) {
                pins.add(currPin);
                r.append(currPin).append(" ");
            }
        }

        return r.toString();
    }
}