package bomesmo.timer.main.core.gui;

import com.main.puzzle.Piece;
import com.main.puzzle.SquareOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class JSquareOne extends JComponent {

    private static final double TAN15 = Math.tan(Math.toRadians(15));
    private static final int TOP_ANCHOR_X = 85;
    private static final int TOP_ANCHOR_Y = 85;
    private static final int BOTTOM_ANCHOR_X = TOP_ANCHOR_X + 160;
    private static final int BOTTOM_ANCHOR_Y = 85;
    private static double l = 100;
    private SquareOne squareOne;

    private int tEdge, tCorner, bEdge, bCorner;

    private MouseAdapter mouseAdapter;

    public JSquareOne() {
        squareOne = new SquareOne();
        setupMouseAdapter();

        setPreferredSize(new Dimension(350, 220));
        setMinimumSize(new Dimension(350, 220));
    }

    public JSquareOne(SquareOne squareOne) {
        this.squareOne = squareOne;
        setupMouseAdapter();

        setPreferredSize(new Dimension(350, 220));
        setMinimumSize(new Dimension(350, 220));
    }

    private static ArrayList<double[]> rotatedPoints(ArrayList<double[]> points, double[] anchorPoint, double angle) {
        ArrayList<double[]> r = new ArrayList<>();

        for (double[] point : points) {
            r.add(rotatedPoint(point, anchorPoint, angle));
        }

        return r;
    }

    private static double[] rotatedPoint(double[] point, double[] anchorPoint, double angle) {
        double x1 = point[0] - anchorPoint[0];
        double y1 = point[1] - anchorPoint[1];

        double x2 = x1 * Math.cos(Math.toRadians(angle)) - y1 * Math.sin(Math.toRadians(angle));
        double y2 = x1 * Math.sin(Math.toRadians(angle)) + y1 * Math.cos(Math.toRadians(angle));

        return new double[]{x2 + anchorPoint[0], y2 + anchorPoint[1]};
    }

    private void setupMouseAdapter() {
        mouseAdapter = new CustomMosueAdapter(
                piecesToDraw(true, TOP_ANCHOR_X, TOP_ANCHOR_Y),
                piecesToDraw(false, BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y));
        addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        drawFace(graphics2D, true, TOP_ANCHOR_X, TOP_ANCHOR_Y);
        drawFace(graphics2D, false, BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y);
    }

    public void drawFace(Graphics2D graphics2D, boolean face, double x, double y) {
        ArrayList<Path2D[]> pieces = piecesToDraw(face, x, y);

        for (int i = 0; i < pieces.size(); i++) {
            for (int j = 0; j < pieces.get(i).length; j++) {
                graphics2D.setColor(getColorByChar(getSquareOne().getPieces(face).get(i).getColors()[j]));
                graphics2D.fill(pieces.get(i)[j]);
                graphics2D.setColor(Color.black);
                graphics2D.draw(pieces.get(i)[j]);
            }
        }

        repaint();
    }

    private ArrayList<Path2D[]> piecesToDraw(boolean face, double x, double y) {
        ArrayList<Path2D[]> pieces = new ArrayList<>();

        boolean prevIsMeio = getSquareOne().getPieces(face).get(0).getColors().length == 2;
        //double currAngle = 0;
        double currAngle = prevIsMeio ? -30 : -45;
        for (Piece p : getSquareOne().getPieces(face)) {
            currAngle += prevIsMeio && p.getColors().length == 2 ? 30 : (!prevIsMeio && p.getColors().length != 2 ? 60 : 45);
            prevIsMeio = p.getColors().length == 2;
            pieces.add(p.getColors().length == 2 ? meioFull(x, y, currAngle) : cantoFull(x, y, currAngle));
        }

        return pieces;
    }

    private Path2D[] cantoFull(double x, double y, double angle) {
        return new Path2D[]{
                canto(x, y, angle),
                lateralCantoA(x, y, angle),
                lateralCantoB(x, y, angle)
        };
    }

    private Path2D[] meioFull(double x, double y, double angle) {
        return new Path2D[]{
                meio(x, y, angle),
                lateralMeio(x, y, angle)
        };
    }

    private Path2D meio(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y},
                        new double[]{x - (l * (TAN15 / 2)), y - (l / 2)},
                        new double[]{x + (l * (TAN15 / 2)), y - (l / 2)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D canto(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y},
                        new double[]{x - (l * 0.2588190451), y - (l * 0.4482877361)},
                        new double[]{x, y - (l * 0.7071067812)},
                        new double[]{x + (l * 0.2588190451), y - (l * 0.4482877361)}));

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralMeio(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x - ((l * Math.tan(Math.toRadians(15))) / 2), y - (l / 2)},
                        new double[]{x + ((l * Math.tan(Math.toRadians(15))) / 2), y - (l / 2)},
                        new double[]{x + ((l * Math.tan(Math.toRadians(15))) / 2), (y - (l / 2)) - (l * 0.07D)},
                        new double[]{x - ((l * Math.tan(Math.toRadians(15))) / 2), (y - (l / 2)) - (l * 0.07D)}));

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoA(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{
                                x - ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                y - ((l * Math.sqrt(2)) / 2) + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))},
                        new double[]{x, y - ((l * Math.sqrt(2)) / 2)},
                        new double[]{x, (y - ((l * Math.sqrt(2)) / 2)) - (l * 0.1D)},
                        new double[]{
                                x - ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                (y - ((l * Math.sqrt(2)) / 2) + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))) - (l * 0.1D)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoB(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y - ((l * Math.sqrt(2)) / 2)},
                        new double[]{
                                x + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                y - ((l * Math.sqrt(2)) / 2) + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))},
                        new double[]{
                                x + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                (y - ((l * Math.sqrt(2)) / 2) + ((l - (l * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))) - (l * 0.1D)},
                        new double[]{x, (y - ((l * Math.sqrt(2)) / 2)) - (l * 0.1)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Color getColorByChar(char sigla) {
        switch (sigla) {
            case 'w':
                return Color.white;
            case 'y':
                return Color.yellow;
            case 'b':
                return Color.blue;
            case 'g':
                return Color.green;
            case 'r':
                return Color.red;
            case 'o':
                return Color.orange;
            default:
                return Color.cyan;
        }
    }

    private boolean isMeio(Path2D path) {
        int numVertices = 0;
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()) {
            if (i.currentSegment(new double[6]) == PathIterator.SEG_LINETO) {
                numVertices++;
            }
        }

        return numVertices + 1 == 3;
    }

    public SquareOne getSquareOne() {
        return squareOne;
    }

    public void setSquareOne(SquareOne squareOne) {
        this.squareOne = squareOne;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        JSquareOne.l = l;
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    private class CustomMosueAdapter extends MouseAdapter {

        private ArrayList<Path2D[]> topDrawedPieces, bottomDrawed;
        private int tEdge, tCorner, bEdge, bCorner;

        public CustomMosueAdapter(ArrayList<Path2D[]> topDrawedPieces, ArrayList<Path2D[]> bottomDrawed) {
            this.topDrawedPieces = topDrawedPieces;
            this.bottomDrawed = bottomDrawed;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            setPiecesIndexClicked(true, e.getPoint());
            setPiecesIndexClicked(false, e.getPoint());
        }

        private void setPiecesIndexClicked(boolean face, Point2D clikcedPoint) {
            ArrayList<Path2D[]> top = face ? topDrawedPieces : bottomDrawed;
            for (int i = 0; i < top.size(); i++) {
                for (int j = 0; j < top.get(i).length; j++) {
                    if (top.get(i)[j].contains(clikcedPoint)) {
                        if (isMeio(top.get(i)[0])) {
                            if (face) {
                                tEdge = i;
                                break;
                            } else {
                                bEdge = i;
                                break;
                            }
                        } else {
                            if (face) {
                                tCorner = i;
                                break;
                            } else {
                                bCorner = i;
                                break;
                            }
                        }
                    }
                }
            }
        }

        public int gettEdge() {
            return tEdge;
        }

        public void settEdge(int tEdge) {
            this.tEdge = tEdge;
        }

        public int gettCorner() {
            return tCorner;
        }

        public void settCorner(int tCorner) {
            this.tCorner = tCorner;
        }

        public int getbEdge() {
            return bEdge;
        }

        public void setbEdge(int bEdge) {
            this.bEdge = bEdge;
        }

        public int getbCorner() {
            return bCorner;
        }

        public void setbCorner(int bCorner) {
            this.bCorner = bCorner;
        }
    }
}