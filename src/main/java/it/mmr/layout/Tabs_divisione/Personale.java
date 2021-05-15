package it.mmr.layout.Tabs_divisione;

import it.mmr.database.Registrazione_database;
import it.mmr.database.Rimozione_database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Personale extends JFrame implements ActionListener {

    public JButton piu, meno;

    public JLayeredPane Personale() {
        // piu_bussiness, piu_motori, piu_dinamica_del_veicolo, piu_powertrain, piu_ricerca;
        //public static JPanel p1;
        JMenuBar barra;

        //ImageIcon icon_piu = new ImageIcon("src/main/java/images/piuu.png");
        //ImageIcon icon_meno = new ImageIcon("src/main/java/images/meno.png");

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
        /*
         * codice per il tasto meno:
         * codice che deve andare in Personale.java
         */

        meno = new JButton(new ImageIcon(resized_icon_meno));
        meno.addActionListener(this);
        meno.setBorder(BorderFactory.createEmptyBorder());
        meno.setContentAreaFilled(false);
        //  meno.addActionListener(this);
        JPanel pmeno = new JPanel();
        pmeno.setBackground(Color.WHITE);
        pmeno.add(meno);
        pmeno.setBounds(1370, 740, 150, 150);


        /*
         * codice per il tasto piu:
         * codice che deve essere messo in Personale.java
         */

        piu = new JButton(new ImageIcon(resized_icon_piu));
        piu.addActionListener(this);

        // piu.addActionListener((ActionListener) this);
        // piu.addActionListener(e);
        //  Bottoni.actionPerformed(e, piu);
        //piu_bussiness = new JButton(icon_piu);

        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);
        //  piu.addActionListener(this);

        /*
         * pannello dedicato al tatso piu
         * codice deve essere spostato in Personale.java
         */

        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setBackground(Color.white);
        pannello_piu.setBounds(1370, 851, 150, 150);

        /*
         * pannello dedicato all'elenco del personale
         * codice da spostare in Personale.java
         */
        JLayeredPane pannello_del_personale = new JLayeredPane();
        pannello_del_personale.add(pannello_piu, 2, 0);
        pannello_del_personale.add(pmeno, 1, 0);
        JPanel colore = new JPanel();
        colore.setSize(1920, 1200);
        pannello_del_personale.add(colore, 0, 0);
        //JTabbedPane prova = new JTabbedPane();
        JPanel pannello_calendario = new JPanel();
        //prova.addTab("Calendario", calle);
        //pannello_del_personale.add(prova, 2, 0);
        /*
         * inizio di creazione della tabella del personale
         * da spostare in Personale.java
         */
        String[] nomi = {"nome",
                "cognome",
                "ruolo"};

        String[][] dati = {
                {"cnienc", "carriero", "videomaker"},

                {"enrico", "garrapa", "nullafacente"},

                {"marco", "cask", "bho"},

                {"fjk", "jnk", "ikln"}
        };

        JTable table = new JTable(dati, nomi);
        table.setEnabled(false);
        table.setBounds(0, 25, 1700, 1000);

        pannello_del_personale.add(table, 0, 0);

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
        pannello_del_personale.add(colonna_ruolo, 0, 0);

        pannello_del_personale.add(colonna_cognome, 1, 0);

        /*setContentPane(pannello_del_personale);
        setSize(1080, 1080);
        setVisible(true);*/
        return pannello_del_personale;


    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == piu){
            new Registrazione_database();
        }
        if(e.getSource() == meno){
            new Rimozione_database();
        }
    }
}