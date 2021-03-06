package bomesmo.paritychecker.main.cale;

import com.main.puzzle.Piece;
import com.main.puzzle.SquareOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaleChecker {

    private SquareOne squareOne;
    private List<Object> pCheckerSearches;
    private long elapsedTime;

    public CaleChecker(SquareOne squareOne) {
        this.squareOne = squareOne;
        pCheckerSearches = Collections.synchronizedList(new ArrayList<>());
    }

    public void searchAll() {
        long i = System.currentTimeMillis();

        for (Piece topEdge : getSquareOne().getPieces(true, true)){
            for (Piece topCorner : getSquareOne().getPieces(true, false)){
                for (Piece bottomEdge : getSquareOne().getPieces(false, true)){
                    for (Piece bottomCorner : getSquareOne().getPieces(false, false)){

                        pCheckerSearches.add(new PCheckerSearch(
                                new Piece[]{topEdge, bottomEdge},
                                new Piece[]{topCorner, bottomCorner},
                                isEven(
                                        new Piece[]{topEdge, bottomEdge},
                                        new Piece[]{topCorner, bottomCorner})));
                    }
                }
            }
        }

        elapsedTime = System.currentTimeMillis() - i;
    }

    public boolean isEven(Piece[] edgesTopBottom, Piece[] cornersTopBottom){
        if (edgesTopBottom != null && cornersTopBottom != null) {
            if (
                    edgesTopBottom[0] != null &&
                            edgesTopBottom[1] != null &&
                            cornersTopBottom[0] != null &&
                            cornersTopBottom[1] != null) {

                int count = 0;
                ArrayList<Piece> auxTriad = getPiecesTriad(edgesTopBottom, 'w');
                count += isParityTriad(auxTriad.get(0).getColors()[1], auxTriad.get(1).getColors()[1], auxTriad.get(2).getColors()[1]) ?
                        1 : 0;

                auxTriad = getPiecesTriad(edgesTopBottom, 'y');
                count += isParityTriad(auxTriad.get(0).getColors()[1], auxTriad.get(1).getColors()[1], auxTriad.get(2).getColors()[1]) ?
                        1 : 0;

                auxTriad = getPiecesTriad(cornersTopBottom, 'w');
                count += isParityTriad(auxTriad.get(0).getColors()[1], auxTriad.get(1).getColors()[1], auxTriad.get(2).getColors()[1]) ?
                        1 : 0;

                auxTriad = getPiecesTriad(cornersTopBottom, 'y');
                count += isParityTriad(auxTriad.get(0).getColors()[1], auxTriad.get(1).getColors()[1], auxTriad.get(2).getColors()[1]) ?
                        1 : 0;

                count += oddYellows(edgesTopBottom, cornersTopBottom) % 2 != 0 ? 1 : 0;

                return count % 2 == 0;
            }
        }

        return false;
    }

    public boolean isParityTriad(char... triad){
        return (areOppsites(triad[0], triad[1]) && isParity(triad[0], triad[2])) ||
                (areOppsites(triad[0], triad[2]) && isParity(triad[1], triad[2])) ||
                (areOppsites(triad[1], triad[2]) && isParity(triad[0], triad[1]));
    }

    public boolean areOppsites(char colorA, char colorB){
        String ab = colorA + "" + colorB;
        return (ab.contains("r") && ab.contains("o")) ||
                (ab.contains("g") && ab.contains("b")) ||
                (ab.contains("w") && ab.contains("y"));
    }

    public boolean isParity(char colorA, char colorB){
        String ab = colorA + "" + colorB;
        return (ab.contains("r") && ab.contains("b")) ||
                (ab.contains("g") && ab.contains("o"));
    }

    public ArrayList<Piece> getPiecesTriad(Piece[] startTopBottom, char color){
        ArrayList<Piece> piecesTop = getSquareOne().getPieces(true);
        ArrayList<Piece> piecesBottom = getSquareOne().getPieces(false);

        Collections.rotate(piecesTop, (piecesTop.indexOf(startTopBottom[0]) * -1));
        Collections.rotate(piecesBottom, (piecesBottom.indexOf(startTopBottom[1]) * -1));

        ArrayList<Piece> a = new ArrayList<>();
        ArrayList<Piece> b = new ArrayList<>();

        for (Piece x : piecesTop){
            if (x.getClass().getName().equals(startTopBottom[0].getClass().getName()) && x.getColors()[0] == color){
                a.add(x);
            }
        }

        for (Piece x : piecesBottom){
            if (x.getClass().getName().equals(startTopBottom[1].getClass().getName()) && x.getColors()[0] == color){
                b.add(x);
            }
        }

        a.addAll(b);
        return a;
    }

    public int oddYellows(Piece[] edgeStartTopBottom, Piece[] cornerStartTopBottom){
        ArrayList<Piece> topEdges = getSquareOne().getPieces(true, true);
        ArrayList<Piece> bottomEdges = getSquareOne().getPieces(false, true);

        Collections.rotate(topEdges, (topEdges.indexOf(edgeStartTopBottom[0]) * -1));
        Collections.rotate(bottomEdges, (bottomEdges.indexOf(edgeStartTopBottom[1]) * -1));

        topEdges.addAll(bottomEdges);
        int oddEdges = 0;
        for (int i = 0; i < topEdges.size(); i++) {
            if (i % 2 == 0){
                if (topEdges.get(i).getColors()[0] == 'y'){
                    oddEdges++;
                }
            }
        }

        ArrayList<Piece> topCorners = getSquareOne().getPieces(true, false);
        ArrayList<Piece> bottomCorners = getSquareOne().getPieces(false, false);

        Collections.rotate(topCorners, (topCorners.indexOf(cornerStartTopBottom[0]) * -1));
        Collections.rotate(bottomCorners, (bottomCorners.indexOf(cornerStartTopBottom[1]) * -1));

        topCorners.addAll(bottomCorners);
        int oddCorners = 0;
        for (int i = 0; i < topCorners.size(); i++) {
            if (i % 2 == 0){
                if (topCorners.get(i).getColors()[0] == 'y'){
                    oddCorners++;
                }
            }
        }

        return oddEdges + oddCorners;
    }

    public SquareOne getSquareOne() {
        return squareOne;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public List<Object> getpCheckerSearches() {
        return pCheckerSearches;
    }

    private class PCheckerSearch {

        private Piece[] edgesTopBottom;
        private Piece[] cornersTopBottom;
        private boolean isEven;

        public PCheckerSearch(Piece[] edgesTopBottom, Piece[] cornersTopBottom, boolean isEven) {
            this.edgesTopBottom = edgesTopBottom;
            this.cornersTopBottom = cornersTopBottom;
            this.isEven = isEven;
        }

        public Piece[] getEdgesTopBottom() {
            return edgesTopBottom;
        }

        public void setEdgesTopBottom(Piece[] edgesTopBottom) {
            this.edgesTopBottom = edgesTopBottom;
        }

        public Piece[] getCornersTopBottom() {
            return cornersTopBottom;
        }

        public void setCornersTopBottom(Piece[] cornersTopBottom) {
            this.cornersTopBottom = cornersTopBottom;
        }

        public boolean isEven() {
            return isEven;
        }

        public void setEven(boolean even) {
            this.isEven = even;
        }

        @Override
        public String toString() {

            return "Starting counting at:\n\ttop edge:      " + Arrays.toString(getEdgesTopBottom()[0].getColors()) +
                    ";\n\t" +
                    "bottom edge:   " + Arrays.toString(getEdgesTopBottom()[1].getColors()) +
                    ";\n\t" +
                    "top corner:    " + Arrays.toString(getCornersTopBottom()[0].getColors()) +
                    ";\n\t" +
                    "bottom corner: " + Arrays.toString(getCornersTopBottom()[1].getColors()) +
                    "\nYou get the result " + (this.isEven() ? "isEven\n\n" : "odd\n\n");
        }
    }
}
