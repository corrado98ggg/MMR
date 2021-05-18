package it.mmr.layout;

import it.mmr.database.DBManager;
import it.mmr.database.Registrazione_database;
import it.mmr.database.Utils;
import it.mmr.layout.Divisioni.*;

import it.mmr.layout.Tabs_divisione.PannelloTotale;
import org.eclipse.jetty.server.PushBuilder;

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

    /* public JButton piu;
     public JButton meno;*/
    JTabbedPane divisioni;
    JButton avvertenza;
    public static int i = 3;
    public static JLayeredPane a;

    public static String warning;

    public Schermata_Principale_home() throws SQLException {
        super("Home");

        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");
        //ImageIcon blocco_note = new ImageIcon("src/main/java/images/blocco_note.jpg");

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

        BufferedImage resized_blocco_note = Registrazione_database.getScaledDimension(blocconote, 500, 500);
        BufferedImage resized_danger = Registrazione_database.getScaledDimension(danger, 60, 75);

        JTabbedPane divisioni = new JTabbedPane(JTabbedPane.LEFT);

        avvertenza = new JButton("Aggiungi avvertenza");
        avvertenza.addActionListener(this);

        a = new JLayeredPane();
        a.add(check_avvertenza(), 2, 2);

        JPanel panello_avvertenza = new JPanel();
        panello_avvertenza.add(avvertenza);
        panello_avvertenza.setBounds(85, 975, 200, 200);

        JLabel picLabel = new JLabel(new ImageIcon(resized_blocco_note));
        JPanel c = new JPanel();

        JLabel picLabel1 = new JLabel(new ImageIcon(resized_danger));
        JPanel panello_danger = new JPanel();

        panello_danger.add(picLabel1);
        panello_danger.setBounds(25, 950, 65, 80);

        c.setBackground(new Color(237, 237, 237));
        c.add(picLabel);
        c.setBounds(20, 330, 300, 300);

        divisioni.add(null, logo_mmr);
        divisioni.addTab("aereodinamica", Aereodinamica.aereodinamica());
        //divisioni.addTab("Business", PannelloTotale.PannelloTotale());
        //divisioni.addTab("motori", PannelloTotale.PannelloTotale());
        //divisioni.addTab("Dinamica del veicolo", PannelloTotale.PannelloTotale());
        //divisioni.addTab("Elettronica", PannelloTotale.PannelloTotale());
        //divisioni.addTab("Powertrain", PannelloTotale.PannelloTotale());
        //divisioni.addTab("Ricerca", PannelloTotale.PannelloTotale());

        divisioni.setSelectedIndex(1);

        divisioni.setSize(1920, 1080);
        a.setBounds(0, 0, 1920, 1080);
        a.add(divisioni, 0, 0);
        a.add(c, 1, 1);

        a.add(panello_avvertenza, 2, 2);
        a.add(panello_danger, 2, 1);

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
        testo.setFont(new Font("Arial Black", Font.BOLD, 10));
        testo.setOpaque(false);
        testo.setEditable(false);


        JPanel d = new JPanel();
        d.setBounds(20, 450, 280, 100);
        d.setBackground(new Color(253, 203, 1));
        d.add(testo);

        return d;
    }

    public static void aggiungi_stringa(String str) throws SQLException {

        try {
            testConnection_avvertenze();
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

    public static void main(String[] args) throws SQLException {
        new Schermata_Principale_home();
    }
}