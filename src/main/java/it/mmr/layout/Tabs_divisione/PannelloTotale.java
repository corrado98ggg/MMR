package it.mmr.layout.Tabs_divisione;

import it.mmr.database.Registrazione_database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class PannelloTotale extends JFrame {

    public static JTabbedPane PannelloTotale() throws SQLException {

        JTabbedPane tabs = new JTabbedPane();
        Personale a = new Personale();
        Calendario eventi = new Calendario();
        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale", a.Personale());
        Eventi h = new Eventi();
        Spese f = new Spese();
        Aiuto j=new Aiuto();


        tabs.addTab("Eventi", h.Eventi());
        tabs.addTab("Andamento", Andamento.Andamento()); //indici più importanti
        tabs.addTab("Spese e sconti", f.Spese()); //tutte le spese effettuate
        try {
            tabs.addTab("Aiuto",j.Aiuto() );
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
