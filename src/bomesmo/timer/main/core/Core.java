package bomesmo.timer.main.core;

import bomesmo.Main;
import bomesmo.paritychecker.main.ParityChecker;
import bomesmo.timer.main.auxiliar.Misc;
import bomesmo.timer.main.core.gui.Gui;
import bomesmo.timer.main.core.gui.Log;
import bomesmo.timer.main.core.scramblers.*;
import bomesmo.timer.main.core.statistics.*;
import com.cs.main.puzzle.Table;
import com.main.puzzle.Piece;
import com.main.puzzle.SquareOne;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Core {

    private Gui gui;
    private ArrayList<Solve> solves;

    private Scramble scramble;
    private String scrambleShown;

    private boolean squareOneEntered;

    public Core(Gui gui){
        this.gui = gui;
        solves = new ArrayList<>();

        this.gui.getPainelSquare().setVisible(false);
        this.gui.getCaleCheckBt().setEnabled(false);

        handleNewScramble(this.gui.getSeletorScramble());

        setupScramblesBox();
        setupShapeA_Box();

        setupDeleteSolve();
        setupDeletarPior();
        setupVerSelecionada();
        setupDetalharStat();
        setupAddCubeShape();
        setupDeletarShapeSelecionado();
        setupSpaceListener();
        setupCaleCheckBt();

        this.gui.setVisible(true);
    }

    private void setupCaleCheckBt() {
        gui.getCaleCheckBt().addActionListener(e -> {
            ParityChecker parityChecker = new ParityChecker(gui.getPreview().getSquareOne());

            Piece[] edgesTopBottom = new Piece[]{
                    gui.getPreview().getCustomMouseAdapter().gettEdge(),
                    gui.getPreview().getCustomMouseAdapter().getbEdge()
            };

            Piece[] cornersTopBottom = new Piece[]{
                    gui.getPreview().getCustomMouseAdapter().gettCorner(),
                    gui.getPreview().getCustomMouseAdapter().getbCorner()
            };

            gui.getParityResultArea().append(parityChecker.isEven(edgesTopBottom, cornersTopBottom) + ".");
        });
    }

    private void setupShapeA_Box(){
        ArrayList<String> shapesA = new ArrayList<>();
        for (Table x : Table.values()){
            if (!shapesA.contains(x.getFaceShape(true).getName())){
                shapesA.add(x.getFaceShape(true).getName());
            }
        }

        shapesA.sort(Comparator.naturalOrder());
        gui.getSeletorShapeA().setModel(new DefaultComboBoxModel<>(shapesA.toArray(new String[shapesA.size()])));
        handleShapeB_Refresh(gui.getSeletorShapeB());
        gui.getSeletorShapeA().addActionListener(e -> handleShapeB_Refresh(gui.getSeletorShapeB()));
    }

    private void setupScramblesBox(){
        gui.getSeletorScramble().addActionListener(e -> handleNewScramble(gui.getSeletorScramble()));
    }

    private void setupAddCubeShape(){
        gui.getBtAddShape().addActionListener(e -> new Thread(() -> {
            String shapeA = (String) gui.getSeletorShapeA().getSelectedItem();
            String shapeB = (String) gui.getSeletorShapeB().getSelectedItem();

            for (Table x : Table.values()){
                if (x.getFaceShape(true).getName().equals(shapeA) && x.getFaceShape(false).getName().equals(shapeB)){
                    if (!Main.shapesPreferidos.contains(x)){
                        System.out.println("Shape adicionado: " + x);
                        Main.shapesPreferidos.add(x);
                    }
                }
            }

            ArrayList<String> adicionados = new ArrayList<>();
            for (Table x : Main.shapesPreferidos){
                adicionados.add(x.getFaceShape(true).getName() + "/" + x.getFaceShape(false).getName());
            }

            gui.getListaAdicionados().setModel(new AbstractListModel<String>() {
                @Override
                public int getSize() {
                    return adicionados.size();
                }

                @Override
                public String getElementAt(int index) {
                    return adicionados.get(index);
                }
            });

            if (Main.shapesPreferidos.size() == 1){
                scramble = new SquareOneScrambler(false);
                gui.getLblScramble().setText("loading....");
                scrambleShown = scramble.getSequence();
                gui.getLblScramble().setText(scrambleShown);
                gui.getPreview().setSquareOne(new SquareOne(scrambleShown));
                this.gui.getCaleCheckBt().setEnabled(true);
            }
        }).start());
    }

    private void setupDeletarShapeSelecionado(){
        gui.getDeletarShapeBt().addActionListener(e -> {
            int selectedShapeIndex = gui.getListaAdicionados().getSelectedIndex();

            if (selectedShapeIndex != -1){
                Main.shapesPreferidos.remove(selectedShapeIndex);

                ArrayList<String> adicionados = new ArrayList<>();
                for (Table x : Main.shapesPreferidos){
                    adicionados.add(x.getFaceShape(true).getName() + "/" + x.getFaceShape(false).getName());
                }

                gui.getListaAdicionados().setModel(new AbstractListModel<String>() {
                    @Override
                    public int getSize() {
                        return adicionados.size();
                    }

                    @Override
                    public String getElementAt(int index) {
                        return adicionados.get(index);
                    }
                });
            }
        });
    }

    private void setupSpaceListener(){
        gui.addKeyListener(
                new SBL(
                        this,

                        //componentes para ativar/desativar ao iniciar
                        gui.getBtDeletar(),
                        gui.getBtDeletarPior(),
                        gui.getBtDetalharEstatistica(),
                        gui.getBtVerSolveSelecionada(),
                        gui.getLblScramble(),
                        gui.getListaEstatisticas(),
                        gui.getListaTempos(),
                        gui.getListaAdicionados(),
                        gui.getSeletorScramble(),
                        gui.getSeletorShapeA(),
                        gui.getSeletorShapeB(),
                        gui.getDeletarShapeBt(),
                        gui.getBtAddShape(),
                        gui.getPreview(),
                        gui.getParityResultArea(),
                        gui.getCaleCheckBt(),
                        gui.getTracingCheckBt()
                )
        );
    }

    private void handleShapeB_Refresh(JComboBox<String> combo){
        combo.setModel(new DefaultComboBoxModel<>(new String[]{"loading..."}));

        new Thread(() -> {
            String selectedA = (String) gui.getSeletorShapeA().getSelectedItem();
            ArrayList<String> shapesB = new ArrayList<>();

            for (Table x : Table.values()){
                if (x.getFaceShape(true).getName().equals(selectedA)){
                    if (!shapesB.contains(x.getFaceShape(false).getName())){
                        shapesB.add(x.getFaceShape(false).getName());
                    }
                }
            }

            shapesB.sort(Comparator.naturalOrder());
            combo.setModel(new DefaultComboBoxModel<>(shapesB.toArray(new String[shapesB.size()])));
        }).start();
    }

    private void handleNewScramble(JComboBox<String> combo){
        gui.getLblScramble().setText("loading....");
        gui.getPainelSquare().setVisible(combo.getSelectedIndex() == 2);
        squareOneEntered = combo.getSelectedIndex() == 2;

        new Thread(() -> {
            switch (combo.getSelectedIndex()){
                case 0:
                    //SET 3X3 SCRAMBLE
                    scramble = new RubiksCubeScrambler();
                    scrambleShown = scramble.getSequence();
                    gui.getLblScramble().setText(scrambleShown);
                    break;
                case 1:
                    //SET 2X2 SCRAMBLE
                    scramble = new PocketScrambler();
                    scrambleShown = scramble.getSequence();
                    gui.getLblScramble().setText(scrambleShown);
                    break;
                case 2:
                    //SET SQ-1 SCRAMBLE
                    scramble = new SquareOneScrambler(Main.shapesPreferidos.size() == 0);
                    scrambleShown = scramble.getSequence();

                    if (!scrambleShown.contains("selecione")) {
                        gui.getPreview().setSquareOne(new SquareOne(scrambleShown));
                    }

                    gui.getLblScramble().setText(scrambleShown);
                    break;
                case 3:
                    //SET SQ-1 CUB SCRAMBLE
                    gui.getLblScramble().setText("SQ-1 CUB SCRAMBLEEEEE");
                    break;
                case 4:
                    //SET SKEWB SCRAMBLE
                    scramble = new SkewbScrambler();
                    scrambleShown = scramble.getSequence();
                    gui.getLblScramble().setText(scrambleShown);
                    break;
                case 5:
                    //SET CLOCK SCRAMBLE
                    scramble = new ClockScrambler();
                    scrambleShown = scramble.getSequence();
                    gui.getLblScramble().setText(scrambleShown);
                    break;
                case 6:
                    //SET MEGA SCRAMBLE
                    gui.getLblScramble().setText("MEGA SCRAMBLEEEEE");
                    break;
                case 7:
                    //SET MEGA LFLL SCRAMBLE
                    gui.getLblScramble().setText("MEGA LFLL SCRAMBLEEEEE");
                    break;
            }
        }).start();
    }

    private void setupDeleteSolve(){
        gui.getBtDeletar().addActionListener(e -> {
            if (!solves.isEmpty()){
                int selectedSolve = gui.getListaTempos().getSelectedIndex();

                if (selectedSolve != -1 && !solves.isEmpty()){
                    solves.remove(selectedSolve);

                    TLR.refresh(gui.getListaTempos(), solves);
                    SLR.refresh(gui.getListaEstatisticas(), solves);
                }
            }
        });
    }

    private void setupDeletarPior(){
        gui.getBtDeletarPior().addActionListener(e -> {
            if (!solves.isEmpty()){
                int worst = Misc.indexOfWorst(solves);
                solves.remove(worst);
                TLR.refresh(gui.getListaTempos(), solves);
                SLR.refresh(gui.getListaEstatisticas(), solves);
            }
        });
    }

    private void setupVerSelecionada(){
        gui.getBtVerSolveSelecionada().addActionListener(e -> {
            if (!solves.isEmpty()){
                int selected = gui.getListaTempos().getSelectedIndex();

                if (selected != -1){
                    new Log(solves.get(selected).toString()).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Não tem solves ainda!");
            }
        });
    }

    //01 34 67 9-10 12-13
    private void setupDetalharStat(){
        gui.getBtDetalharEstatistica().addActionListener(e -> {
            //int i = gui.getListaEstatisticas().getSelectedIndex();

            String elemento = gui.getListaEstatisticas().getSelectedValue();
            Log log = new Log(null);

            if (!elemento.startsWith("/")){
                if (elemento.contains("melhor tempo")){
                    log = new Log(new Best(solves).details());
                }
                if (elemento.contains("pior tempo")){
                    log = new Log(new Worst(solves).details());
                }

                if (elemento.contains("média geral")){
                    log = new Log(new OverrMean(solves).details());
                }
                if (elemento.contains("average geral")){
                    log = new Log(new OverrAverage(solves).details());
                }

                if (elemento.contains("melhor avg5")){
                    log = new Log(new BestAvgX(solves, 5).details());
                }
                if (elemento.contains("current avg5")){
                    log = new Log(new CurrentAvgX(solves, 5).details());
                }
                if (elemento.contains("pior avg5")){
                    log = new Log(new WorstAvgX(solves, 5).details());
                }

                if (elemento.contains("melhor avg12")){
                    log = new Log(new BestAvgX(solves, 12).details());
                }
                if (elemento.contains("current avg12")){
                    log = new Log(new CurrentAvgX(solves, 12).details());
                }
                if (elemento.contains("pior avg12")){
                    log = new Log(new WorstAvgX(solves, 12).details());
                }

                if (elemento.contains("melhor avg50")){
                    log = new Log(new BestAvgX(solves, 50).details());
                }
                if (elemento.contains("current avg50")){
                    log = new Log(new CurrentAvgX(solves, 50).details());
                }
                if (elemento.contains("pior avg50")){
                    log = new Log(new WorstAvgX(solves, 50).details());
                }

                if (elemento.contains("melhor avg100")){
                    log = new Log(new BestAvgX(solves, 100).details());
                }
                if (elemento.contains("current avg100")){
                    log = new Log(new CurrentAvgX(solves, 100).details());
                }
                if (elemento.contains("pior avg100")){
                    log = new Log(new WorstAvgX(solves, 100).details());
                }

                log.setTitle(elemento);
                log.setVisible(true);
            }
        });
    }

    public ArrayList<Solve> getSolves() {
        return solves;
    }

    public Gui getGui() {
        return gui;
    }

    public Scramble getScramble() {
        return scramble;
    }

    public boolean isSquareOneEntered() {
        return squareOneEntered;
    }
}