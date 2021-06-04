package it.mmr.layout.Divisioni;

import it.mmr.layout.Tabs_divisione.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Business {

    public JTabbedPane Business() throws SQLException {
        /*
         * tabs va spostato in PannelloTotale.java
         */
        JTabbedPane tabs = new JTabbedPane();
        /*
         * tabs va spostato in PannelloTotale.jav
         */



        tabs.setBackground(Color.CYAN);
        //Calendario b=new Calendario();
        //JLayeredPane pro=new JLayeredPane();
        // pro.add(eventi.Calendario());
        Eventi h = new Eventi();
        Spese f = new Spese();
        Personale a = new Personale();
        Calendario eventi = new Calendario();

        tabs.addTab("Personale", a.Personale());
        tabs.addTab("Eventi", h.Eventi());
        tabs.addTab("Andamento", Andamento.Andamento()); //indici più importanti
        tabs.addTab("Spese e sconti", f.Spese()); //tutte le spese effettuate
        //tabs.addTab("Sponsor", null);
        tabs.addTab("Aiuto", null);

        /*setVisible(true);
        setContentPane(tabs);
        setSize(1920, 1080);*/
        System.out.println("...............................................");
        System.out.println(tabs.getTabComponentAt(1));
        System.out.println("...............................................");
        return tabs;
    }
}
