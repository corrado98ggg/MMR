package it.mmr.layout;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.accesso.Login_iniziale;
import it.mmr.database.*;
import it.mmr.layout.Tabs_divisione.PannelloTotale;
import it.mmr.layout.Tabs_divisione.Personale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Schermata_Principale_home extends JFrame implements ActionListener {

    JButton avvertenza;
    JButton cambia_password;
    public static int i = 3;
    public static JLayeredPane contenitore;
    public static String warning;
    JButton sito;

    public JButton instagram, facebook;


    public Schermata_Principale_home() throws SQLException {
        super("Home");

        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");

        BufferedImage resized_blocco_note = Creazione_immagini.creazioneImmagini("src/main/java/images/AVVERTENZE.png", 500, 500);
        BufferedImage resized_instagram = Creazione_immagini.creazioneImmagini("src/main/java/images/instagram.png", 90, 90);
        BufferedImage resized_facebook = Creazione_immagini.creazioneImmagini("src/main/java/images/facebook.png", 90, 90);
        BufferedImage resized_danger = Creazione_immagini.creazioneImmagini("src/main/java/images/danger.png", 60, 75);
        BufferedImage resized_gokart = Creazione_immagini.creazioneImmagini("src/main/java/images/gokart.png", 90, 90);

        sito = new JButton("Portami al sito web");
        sito.setForeground(new Color(0x0EAAE7));
        sito.addActionListener(this);
        sito.setBorder(BorderFactory.createEmptyBorder());
        sito.setContentAreaFilled(false);
        sito.setFont(new Font("MONACO", Font.ITALIC, 20));

        JLabel pannello_gokart = new JLabel(new ImageIcon(resized_gokart));
        pannello_gokart.setVisible(true);
        pannello_gokart.setBounds(60, 620, 450, 90);

        JPanel pannello_sito = new JPanel(new GridBagLayout());
        pannello_sito.add(sito);
        pannello_sito.setBounds(-30, 620, 280, 90);

        JTabbedPane divisioni = new JTabbedPane(JTabbedPane.LEFT);

        avvertenza = new JButton("Aggiungi avvertenza");
        avvertenza.addActionListener(this);

        cambia_password = new JButton("Cambia Password");
        cambia_password.addActionListener(this);

        contenitore = new JLayeredPane();
        contenitore.add(check_avvertenza(), 2, 2);

        JPanel panello_avvertenza = new JPanel();
        panello_avvertenza.add(avvertenza);
        panello_avvertenza.setBounds(85, 975, 200, 200);

        JPanel panello_cambio_password = new JPanel();
        panello_cambio_password.add(cambia_password);
        panello_cambio_password.setBounds(60, 310, 200, 30);

        JLabel picLabel = new JLabel(new ImageIcon(resized_blocco_note));
        JPanel pannelo_blocconote = new JPanel();

        JLabel picLabel1 = new JLabel(new ImageIcon(resized_danger));
        JPanel panello_danger = new JPanel();

        instagram = new JButton(new ImageIcon(resized_instagram));
        instagram.addActionListener(this);
        instagram.setContentAreaFilled(false);
        instagram.setBorder(BorderFactory.createEmptyBorder());

        instagram.setOpaque(false);

        JPanel pannello_instagram = new JPanel();
        pannello_instagram.add(instagram);
        pannello_instagram.setBounds(40, 200, 90, 90);

        facebook = new JButton(new ImageIcon(resized_facebook));
        facebook.addActionListener(this);
        facebook.setBorder(BorderFactory.createEmptyBorder());

        JPanel pannello_facebook = new JPanel();
        pannello_facebook.setOpaque(true);
        pannello_facebook.add(facebook);
        pannello_facebook.setBounds(160, 200, 150, 150);

        panello_danger.add(picLabel1);
        panello_danger.setBounds(25, 950, 65, 80);

        pannelo_blocconote.setBackground(new Color(237, 237, 237));
        pannelo_blocconote.add(picLabel);
        pannelo_blocconote.setBounds(20, 330, 300, 300);

        divisioni.addTab("", logo_mmr, PannelloTotale.pannelloTotale());
        divisioni.setSize(1920, 1080);
        contenitore.setBounds(0, 0, 1920, 1080);
        contenitore.add(divisioni, 0, 0);
        contenitore.add(pannelo_blocconote, 1, 1);
        contenitore.add(pannello_gokart,1,1);
        contenitore.add(pannello_instagram, 1,1);
        contenitore.add(pannello_facebook,1,1);
        contenitore.add(pannello_sito,1,1);

        if (Login_iniziale.root) {
            contenitore.add(panello_avvertenza, 2, 2);
            contenitore.add(panello_danger, 2, 1);
        }
        contenitore.add(panello_cambio_password, 2, 2);
        setContentPane(contenitore);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setVisible(true);

        try {
            testConnection_avvertenze();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == avvertenza) {
            new Avvertenze();
        }
        if (e.getSource() == cambia_password) {
            new cambio_password();
        }

        if(e.getSource() == facebook){
            try {Desktop.getDesktop().browse(new URI("https://www.facebook.com/MMR.Modena/"));
            } catch (Exception e2)
            {JOptionPane.showMessageDialog(null, e2);}
        }
        if(e.getSource() == instagram){
            try {Desktop.getDesktop().browse(new URI("https://www.instagram.com/mmr_modena/"));
            } catch (Exception e2)
            {JOptionPane.showMessageDialog(null,e2);}
        }

        if(e.getSource() == sito){
            try {Desktop.getDesktop().browse(new URI("https://en.moremodenaracing.it/"));
            } catch (Exception e2)
            {JOptionPane.showMessageDialog(null,e2);}

        }
    }

    public static void testConnection_avvertenze() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver_SQLite,
                Utils.JDBC_URL_SQLite);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM avvertenze");
        } catch (SQLException e) {
            statement.executeUpdate("DROP TABLE IF EXISTS avvertenze");
            statement.executeUpdate("CREATE TABLE avvertenze (" + "testo LONGVARCHAR PRIMARY KEY)");
        }
    }

    public static JPanel check_avvertenza() throws SQLException {

        try {
            testConnection_avvertenze();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet tmp = statement.executeQuery("SELECT testo FROM avvertenze");

        while (tmp.next()) {

            warning = tmp.getString("testo");
            //System.out.println(warning);
        }

        JTextArea testo = new JTextArea(warning);
        testo.setLineWrap(true);
        testo.setWrapStyleWord(true);
        testo.setFont(new Font("Arial Black", Font.BOLD, 15));
        testo.setSize(250, 100);
        testo.setOpaque(false);
        testo.setEditable(false);

        JPanel pannello_avvertenza = new JPanel(new GridBagLayout());
        pannello_avvertenza.setBackground(new Color(253, 203, 1));
        pannello_avvertenza.setBounds(40, 440, 250, 100);
        pannello_avvertenza.add(testo);

        return pannello_avvertenza;
    }

    public static void aggiungi_stringa(String str) throws SQLException {

        try {
            Registrazione_database.testConnection();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        try {
            String query = String.format(
                    "INSERT INTO avvertenze (testo) VALUES ('%s')",
                    str);
            Statement statement = DBManager.getConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void aggiungi_ruolo() throws SQLException {
        try {
            Registrazione_database.testConnection();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet tmp = statement.executeQuery("SELECT * FROM registrazioni");

        int i = 0;

        while (tmp.next()) {

            if (i >= Utils.quante_persone_sono_registrate()) {
                continue;
            }
            for (int j = 0; j < 1; j++) {

                if (Personale.dati[i][j].equals(tmp.getString("nome"))) {
                    if (Personale.dati[i][j + 1].equals(tmp.getString("cognome"))) {

                        try {
                            String query = String.format(
                                    "UPDATE registrazioni SET Ruoli=('%s') WHERE Nome IS ('%s') AND Cognome IS ('%s');",
                                    Personale.ruoli_modificato, Personale.nome, Personale.cognome);
                            Statement statement_2 = DBManager.getConnection().createStatement();
                            statement_2.executeUpdate(query);
                            statement_2.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            i++;
        }
    }
    public static void main(String[] args) throws SQLException { new Schermata_Principale_home(); }
}