package it.mmr.layout.Tabs_divisione;

import it.mmr.database.Registrazione_database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PannelloTotale extends JFrame{


    public static JTabbedPane PannelloTotale(){

        /*
         * tabs va spostato in PannelloTotale.java
         */
        JTabbedPane tabs = new JTabbedPane();
        /*
         * tabs va spostato in PannelloTotale.jav
         */

        Personale a = new Personale();
        //Calendario eventi = new Calendario();
        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale", a.Personale());
        tabs.addTab("Eventi",Calendario.Calendario());


        /*setVisible(true);
        setContentPane(tabs);
        setSize(1920, 1080);*/
        return tabs;

    }

    public static void main(String[] args) {
        new PannelloTotale();
    }


}
