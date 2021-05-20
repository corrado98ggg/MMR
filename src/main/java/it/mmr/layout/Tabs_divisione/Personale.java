package it.mmr.layout.Tabs_divisione;

import it.mmr.database.DBManager;
import it.mmr.database.Registrazione_database;
import it.mmr.database.Rimozione_database;
import it.mmr.database.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Personale extends JFrame implements ActionListener {

    private static int contatore_persone;

    public static String[][] dati;

    public static String[] nomi = {"nome",
            "cognome"};

    public static JButton matita;

    public static String[][] dati_ruoli; //[contatore_persone][1]

    public static JLayeredPane pannello_del_personale = new JLayeredPane();


    public static JTable table_ruoli;

    public JButton piu, meno;

    public JLayeredPane Personale() throws SQLException {

        BufferedImage icon_meno = null;
        try {
            icon_meno = ImageIO.read(new File("src/main/java/images/meno.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage resized_icon_meno = Registrazione_database.getScaledDimension(icon_meno, 109, 109);

        BufferedImage icon_piu = null;
        try {
            icon_piu = ImageIO.read(new File("src/main/java/images/piuu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage resized_icon_piu = Registrazione_database.getScaledDimension(icon_piu, 100, 100);

        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");

        meno = new JButton(new ImageIcon(resized_icon_meno));
        meno.addActionListener(this);
        meno.setBorder(BorderFactory.createEmptyBorder());
        meno.setContentAreaFilled(false);
        JPanel pmeno = new JPanel();
        pmeno.setBackground(Color.WHITE);
        pmeno.add(meno);
        pmeno.setBounds(1370, 740, 150, 150);

        piu = new JButton(new ImageIcon(resized_icon_piu));
        piu.addActionListener(this);

        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);

        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setBackground(Color.white);
        pannello_piu.setBounds(1370, 851, 150, 150);

        pannello_del_personale.add(pannello_piu, 2, 0);
        pannello_del_personale.add(pmeno, 1, 0);
        JPanel colore = new JPanel();
        colore.setSize(1920, 1200);
        pannello_del_personale.add(colore, 0, 0);
        JPanel pannello_calendario = new JPanel();

        Personale.Stampa_personale(Personale.Matrice_personale());

        Personale.Stampa_ruoli(Personale.Matrice_ruoli());

        //table_ruoli.addAncestorListener();
        System.out.println(table_ruoli.getValueAt(0,0).toString());




        JLabel testa_nome = new JLabel("nome");
        JLabel testa_cognome = new JLabel("cognome");
        JLabel testa_ruolo = new JLabel("ruolo");

        JPanel colonna_nome = new JPanel();

        colonna_nome.add(testa_nome);
        colonna_nome.setBounds(0, 0, 50, 25);
        pannello_del_personale.add(colonna_nome, 2, 0);


        JPanel colonna_cognome = new JPanel();

        colonna_cognome.add(testa_cognome);
        colonna_cognome.setBounds(565, 0, 70, 25);


        JPanel colonna_ruolo = new JPanel();

        colonna_ruolo.add(testa_ruolo);
        colonna_ruolo.setBounds(1130, 0, 50, 25);
        pannello_del_personale.add(colonna_ruolo, 1, 0);

        pannello_del_personale.add(colonna_cognome, 2, 0);
        JPanel sfondo = new JPanel();
        sfondo.setBounds(0,40,1920,1000);
        sfondo.setBackground(Color.white);
        pannello_del_personale.add(sfondo, 0, 0);
        Personale a=new Personale();
        pannello_del_personale.add(a.Stampa_matita(),0,0);
        return pannello_del_personale;
    }

    public static String[][] Matrice_personale () throws SQLException {

        try {
            contatore_persone = Utils.quante_persone_sono_registrate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dati = new String[contatore_persone][2];
        int i = 0;

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM registrazioni LIMIT 100");

        while (queryPersonale.next()) {
            if (i >= contatore_persone) {
                continue;
            }
            for (int j = 1; j < 3; j++) {

                //caso base:
                if (i == 0 && j == 1) {
                    dati[i][j - 1] = queryPersonale.getString("nome");
                    System.out.println(dati[i][j - 1]);

                    continue;
                }
                if (j / 2 == 1) {
                    dati[i][j - 1] = queryPersonale.getString("cognome");
                    System.out.println(dati[i][j - 1]);
                }

                if (j / 2 != 1) {
                    dati[i][j - 1] = queryPersonale.getString("nome");
                    System.out.println(dati[i][j - 1]);
                }
            }
            i++;
        }
        return dati;
    }


    public static void Stampa_personale(String[][] tmp){

        JTable table = new JTable(dati, nomi);
        table.setRowHeight(35);
        table.setEnabled(false);
        table.setBounds(0, 25, 1140, 1000);
        pannello_del_personale.add(table, 1, 0);

    }

    public static String[][] Matrice_ruoli() throws SQLException {

        try {
            contatore_persone = Utils.quante_persone_sono_registrate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dati_ruoli = new String[contatore_persone][1];
        int i = 0;

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM registrazioni LIMIT 100");

        while (queryPersonale.next()) {
            if (i >= contatore_persone) {
                continue;
            }

            dati_ruoli[i][0] = queryPersonale.getString("ruoli");
            //System.out.println(dati_ruoli[i][0]);

            i++;
        }
        return dati_ruoli;
    }


    public static void Stampa_ruoli(String[][] tmp) {

        String[] ruoli = {"ruoli"};

        table_ruoli = new JTable(dati_ruoli, ruoli);
        table_ruoli.setRowHeight(35);
        table_ruoli.setEnabled(false);
        table_ruoli.setBounds(1140, 25, 300, 2000);
        pannello_del_personale.add(table_ruoli, 1, 0);


    }

    public  JPanel Stampa_matita(){


        BufferedImage icon_matita = null;
        try {
            icon_matita = ImageIO.read(new File("src/main/java/images/matita.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage resized_icon_matita = Registrazione_database.getScaledDimension(icon_matita, 30, 30);


        matita = new JButton(new ImageIcon(resized_icon_matita));
        matita.addActionListener(this);

        matita.setBorder(BorderFactory.createEmptyBorder());
        matita.setContentAreaFilled(false);
        JPanel matita_panel = new JPanel();
        matita_panel.setBackground(Color.WHITE);
        matita_panel.add(matita);
        matita_panel.setBackground(Color.white);
        matita_panel.setBounds(1410, 26, 150, 35);


        return matita_panel;
    }


        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == piu) {
            new Registrazione_database();
        }
        if (e.getSource() == meno) {
            new Rimozione_database();
        }

        if(e.getSource() == matita){
            new Add_ruolo();
        }
    }

}