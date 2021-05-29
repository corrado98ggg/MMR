package it.mmr.layout.Tabs_divisione;


import it.mmr.database.Nuova_spesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class Spese extends JFrame implements ActionListener, TableModelListener {

    public static String[][] dati;

    boolean c = true;

    public static String[] nomi = {"Descrzione",
            "Qt.", "Prezzo al pz", "Importo"};

    public static JButton matita;

    public static JLayeredPane pannello_della_tabella = new JLayeredPane();

    public JButton piu, meno;

    public JLayeredPane Spese() throws SQLException {
pannello_della_tabella.setSize(300,300);
        piu = new JButton(new ImageIcon(Personale.resized_icon_piu));
        piu.addActionListener(this);
        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);
        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setBackground(Color.white);
        pannello_piu.setBounds(1430, 851, 150, 150);

        pannello_della_tabella.add(pannello_piu, 2, 0);
        JPanel colore = new JPanel();
        colore.setSize(1920, 1200);
        pannello_della_tabella.add(colore, 0, 0);
        Personale tabella =new Personale();
        tabella.Stampa_personale(Personale.Matrice_personale());

        JLabel testa_Descrzione = new JLabel("Descrizione");
        JLabel testa_Qt = new JLabel("Qt.");
        JLabel testa_Prezzo_al_pezzo = new JLabel("Prezzo al pezzo");
        JLabel testa_Importo= new JLabel("Importo");

        JPanel colonna_Descrzione = new JPanel();
        colonna_Descrzione.add(testa_Descrzione);
        colonna_Descrzione.setBounds(0, 0, 90, 25);

        JPanel colonna_Qt = new JPanel();
        colonna_Qt.add(testa_Qt);
        colonna_Qt.setBounds(500, 0, 60, 25);

        JPanel colonna_Prezzo_al_pezzo = new JPanel();
        colonna_Prezzo_al_pezzo.add(testa_Prezzo_al_pezzo);
        colonna_Prezzo_al_pezzo.setBounds(700, 0, 120, 25);

        JPanel colonna_importo = new JPanel();
        colonna_importo.add(testa_Importo);
        colonna_importo.setBounds(1200, 0, 60, 25);

        pannello_della_tabella.add(colonna_Descrzione, 1, 0);
        pannello_della_tabella.add(colonna_Qt, 2, 0);
        pannello_della_tabella.add(colonna_Prezzo_al_pezzo, 3, 0);
        pannello_della_tabella.add(colonna_importo, 4, 0);

        JPanel sfondo = new JPanel();
        sfondo.setBounds(0,40,1920,1000);
        sfondo.setBackground(Color.white);
        pannello_della_tabella.add(sfondo, 0, 0);

        return pannello_della_tabella;

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == piu){
            new Nuova_spesa();
        }


    }

    /**
     * This fine grain notification tells listeners the exact range
     * of cells, rows, or columns that changed.
     *
     * @param e a {@code TableModelEvent} to notify listener that a table model
     *          has changed
     */
    @Override
    public void tableChanged(TableModelEvent e) {

    }


}
