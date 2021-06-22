package it.mmr.layout.Tabs_divisione;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class PannelloTotale extends JFrame {

    public static JTabbedPane pannelloTotale() throws SQLException {

        JTabbedPane tabs = new JTabbedPane();
        Personale a = new Personale();
        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale", a.personale());
        Eventi h = new Eventi();
        Spese f = new Spese();
        Aiuto j=new Aiuto();


        tabs.addTab("Eventi", h.eventi());
        tabs.addTab("Andamento", Andamento.andamento()); //indici più importanti
        tabs.addTab("Spese e sconti", f.spese()); //tutte le spese effettuate
        try {
            tabs.addTab("Aiuto",j.aiuto() );
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("...............................................");
        System.out.println(tabs.getTabComponentAt(1));
        System.out.println("...............................................");
        return tabs;

    }

    public static void main(String[] args) {
        new PannelloTotale();
    }


}
