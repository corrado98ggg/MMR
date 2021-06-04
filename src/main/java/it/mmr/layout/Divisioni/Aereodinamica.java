package it.mmr.layout.Divisioni;

import it.mmr.layout.Tabs_divisione.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Aereodinamica {
    public static JTabbedPane aereodinamica() throws SQLException {
        /*
         * tabs va spostato in PannelloTotale.java
         */
        JTabbedPane tabs = new JTabbedPane();
        /*
         * tabs va spostato in PannelloTotale.jav
         */

        Personale a = new Personale();
        Calendario eventi = new Calendario();
        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale", a.Personale());
        //Calendario b=new Calendario();
        //JLayeredPane pro=new JLayeredPane();
        // pro.add(eventi.Calendario());
        Eventi h = new Eventi();
        Spese f = new Spese();

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
