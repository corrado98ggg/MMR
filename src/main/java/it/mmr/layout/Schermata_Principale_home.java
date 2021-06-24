package it.mmr.layout;

import it.mmr.accesso.Login_iniziale;
import it.mmr.database.*;
import it.mmr.layout.Tabs_divisione.PannelloTotale;
import it.mmr.layout.Tabs_divisione.Personale;

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

public class Schermata_Principale_home extends JFrame implements ActionListener {

    JButton avvertenza;
    JButton cambia_password;
    public static int i = 3;
    public static JLayeredPane a;
    public static String warning;

    public Schermata_Principale_home() throws SQLException {
        super("Home");

        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");

        BufferedImage blocconote = null;
        try {
            blocconote = ImageIO.read(new File("src/main/java/images/AVVERTENZE.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage danger = null;
        try {
            danger = ImageIO.read(new File("src/main/java/images/danger.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert blocconote != null;
        BufferedImage resized_blocco_note = Registrazione_database.getScaledDimension(blocconote, 500, 500);
        assert danger != null;
        BufferedImage resized_danger = Registrazione_database.getScaledDimension(danger, 60, 75);

        JTabbedPane divisioni = new JTabbedPane(JTabbedPane.LEFT);

        avvertenza = new JButton("Aggiungi avvertenza");
        avvertenza.addActionListener(this);

        cambia_password = new JButton("Cambia Password");
        cambia_password.addActionListener(this);

        a = new JLayeredPane();
        a.add(check_avvertenza(), 2, 2);

        JPanel panello_avvertenza = new JPanel();
        panello_avvertenza.add(avvertenza);
        panello_avvertenza.setBounds(85, 975, 200, 200);

        JPanel panello_cambio_password = new JPanel();
        panello_cambio_password.add(cambia_password);
        if (Login_iniziale.root) {
            panello_cambio_password.setBounds(80, 900, 200, 30);
        } else {
            panello_cambio_password.setBounds(80, 970, 200, 30);
        }

        JLabel picLabel = new JLabel(new ImageIcon(resized_blocco_note));
        JPanel c = new JPanel();

        JLabel picLabel1 = new JLabel(new ImageIcon(resized_danger));
        JPanel panello_danger = new JPanel();

        panello_danger.add(picLabel1);
        panello_danger.setBounds(25, 950, 65, 80);

        c.setBackground(new Color(237, 237, 237));
        c.add(picLabel);
        c.setBounds(20, 330, 300, 300);
        divisioni.addTab("", logo_mmr, PannelloTotale.pannelloTotale());
        divisioni.setSize(1920, 1080);
        a.setBounds(0, 0, 1920, 1080);
        a.add(divisioni, 0, 0);
        a.add(c, 1, 1);
        if (Login_iniziale.root) {
            a.add(panello_avvertenza, 2, 2);
            a.add(panello_danger, 2, 1);
        }
        a.add(panello_cambio_password, 2, 2);

        setContentPane(a);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setVisible(true);

        try {
            testConnection_avvertenze();
            //load();
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
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet tmp = statement.executeQuery("SELECT testo FROM avvertenze");

        while (tmp.next()) {

            warning = tmp.getString("testo");
            System.out.println(warning);
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
            //load();
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
            //load();
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

    public static void main(String[] args) throws SQLException {
        new Schermata_Principale_home();
    }
}