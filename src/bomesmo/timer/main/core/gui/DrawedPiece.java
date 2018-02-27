package bomesmo.timer.main.core.gui;

import com.main.puzzle.Piece;

import java.awt.geom.Path2D;

//A holder class
public class DrawedPiece {

    private Path2D[] piecePolygons;
    private Piece piece;

    public DrawedPiece(Path2D[] piecePolygons, Piece piece) {
        this.piecePolygons = piecePolygons;
        this.piece = piece;
    }

    public Path2D[] getPiecePolygons() {
        return piecePolygons;
    }

    public Piece getPiece() {
        return piece;
    }
}
