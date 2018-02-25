package bomesmo.timer.main.core.scramblers;


import bomesmo.Main;
import com.cs.main.puzzle.SquareUtils;
import com.cs.main.puzzle.Table;

public class SquareOneScrambler extends Scramble{

    private boolean vazio;

    public SquareOneScrambler(boolean vazio) {
        this.vazio = vazio;
    }

    @Override
    public String getSequence() {
//        Table[] targets = {
////                Table.KITE_KITE,
////                Table.BARREL_BARREL,
////                Table.SHIELD_SQUARE,
////
////                Table.KITE_SCALLOP,
////                Table.SCALLOP_SCALLOP,
////
////                Table.x411_PARALLEL,
////                Table.x411_PAIRED,
////                Table.x411_PERPENDICULAR,
//
//                //esses são os que mais tenho dificuldades
//                Table.x222_PARALLEL,
//                Table.x222_PERPENDICULAR,
//                Table.x222_PAIRED
//        };

        //return SquareUtils.getRandomStateScrambleToShape(targets, false);
        return isVazio() ? "selecione os shapes e inicie uma nova solve." :
                SquareUtils.getRandomStateScrambleToShape(Main.shapesPreferidos.toArray(new Table[Main.shapesPreferidos.size()]), false);
    }

    public boolean isVazio() {
        return vazio;
    }

    @Override
    public String[] getSequenceMovements() {
        return new String[0];
    }
}
