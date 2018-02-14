package bomesmo.main.core;

import bomesmo.main.auxiliar.TF;

import javax.swing.*;
import java.util.ArrayList;

//Times List Refresher
public class TLR {

    public static void refresh(JList<String> lista, ArrayList<Solve> solves){
        lista.setModel(new AbstractListModel<String>() {
            String[] x = indexedList(solves);

            @Override
            public int getSize() {
                return x.length;
            }

            @Override
            public String getElementAt(int index) {
                return x[index];
            }
        });
    }

    private static String[] indexedList(ArrayList<Solve> solves){
        String[] r = new String[solves.size()];

        for (int i = 0; i < r.length; i++){
            r[i] = (i+1) + ") " + (TF.format(solves.get(i).getTempo()));
        }

        return !solves.isEmpty() ? r : new String[]{"[sem solves]"};
    }
}