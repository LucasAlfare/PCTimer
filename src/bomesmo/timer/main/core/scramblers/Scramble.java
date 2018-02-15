package bomesmo.timer.main.core.scramblers;

import java.util.Random;

public abstract class Scramble {

    public Random rnd = new Random();

    public String[]
            pocketMoves = {"R", "U", "F"},

    rubiksMoves = {"Rx", "Lx", "Uy", "Dy", "Fz", "Bz"},

    clockPins = {"UR", "DR", "DL", "UL"},

    skewbMoves = {"R", "L", "U", "B"},

    skewbDirections = {"' ", " "},

    normalDirections = {"' ", "2 ", " "};

    public abstract String getSequence();
}