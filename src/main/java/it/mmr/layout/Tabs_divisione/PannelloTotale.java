package it.mmr.layout.Tabs_divisione;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class PannelloTotale extends JFrame {

    public static JTabbedPane pannelloTotale() throws SQLException {

        JTabbedPane tabs = new JTabbedPane();

        Personale obj_personale = new Personale();
        Eventi obj_eventi = new Eventi();
        Spese obj_spese = new Spese();
        Aiuto obj_aiuto = new Aiuto();

        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale", obj_personale.personale());
        tabs.addTab("Eventi", obj_eventi.eventi());
        tabs.addTab("Andamento", Andamento.andamento()); //indici più importanti
        tabs.addTab("Spese e sconti", obj_spese.spese()); //tutte le spese effettuate
        try {
            tabs.addTab("Aiuto",obj_aiuto.aiuto() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("...............................................");
        //System.out.println(tabs.getTabComponentAt(1));
        //System.out.println("...............................................");
        return tabs;
    }

    public static void main(String[] args) {
        new PannelloTotale();
    }
}