package it.mmr.layout.Tabs_divisione;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.database.DBManager;
import it.mmr.database.Nuova_spesa;
import it.mmr.database.Registrazione_database;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Andamento extends JFrame {

    public static JPanel sfondo;
    public static int soldi = 0;
    public static BufferedImage icona1;
    public static BufferedImage icona2;
    public static BufferedImage icona3;
    public static BufferedImage icona4;

    public static JLayeredPane andamento() throws SQLException {

        JLayeredPane tot = new JLayeredPane();
        tot.setBounds(0, 0, 200, 200);

        icona1 = Creazione_immagini.Creazione_immagini(  "src/main/java/images/crediti.png", 60, 60);
        icona2 = Creazione_immagini.Creazione_immagini("src/main/java/images/acquisti.png", 95, 95);
        icona3 = Creazione_immagini.Creazione_immagini("src/main/java/images/debito.png", 60, 60);
        icona4 = Creazione_immagini.Creazione_immagini("src/main/java/images/fondo_cassa.jpg", 95, 95);

        tot.add(indice(icona4, "Totale", 678998, "Soldi rimanenti", Color.green), 1, 0);
        JLayeredPane a = indice(icona2, "Spese", calcolo_soldi(), "Documenti ingresso anno corrente", Color.red);
        JLayeredPane b = indice(icona3, "Debito", 345678, "Debiti al 12/10/18", Color.red);
        JLayeredPane c = indice(icona1, "crediti", 759202, "documenti in ingresso anno correte", Color.green);

        sfondo = new JPanel();
        sfondo.setBackground(Color.white);
        sfondo.setBounds(0, 0, 1920, 1080);


        b.setBounds(0, 600, 400, 400);
        b.setBackground(Color.white);
        a.setBounds(0, 400, 400, 400);
        a.setBackground(Color.white);
        c.setBounds(0, 200, 400, 400);
        c.setBackground(Color.white);

        tot.add(sfondo, 0, 0);
        tot.add(a, 3, 0);
        tot.add(c, 2, 0);
        tot.add(b, 4, 0);
        return tot;
    }

    public static JLayeredPane indice(BufferedImage icona, String s, int h, String desc, Color color_text) {
        JLayeredPane contenitore = new JLayeredPane();


        JLabel crediti = new JLabel(s);
        crediti.setForeground(color_text);
        crediti.setFont(new Font("Monaco", Font.BOLD, 15));
        JPanel titolo = new JPanel();
        titolo.setBackground(Color.white);
        titolo.add(crediti);
        titolo.setBounds(60, 0, 60, 20);
        contenitore.add(titolo, 1, 0);


        JLabel icon = new JLabel(new ImageIcon(icona));
        JPanel pannello_icona = new JPanel();
        pannello_icona.setBackground(Color.white);
        pannello_icona.setBounds(20, 20, 60, 70);
        pannello_icona.add(icon);
        contenitore.add(pannello_icona, 6, 0);


        JLabel prezzo = new JLabel(h + " €");
        // prezzo.setEnabled(false);
        prezzo.setFont(new Font("Monaco", Font.BOLD, 20));
        prezzo.setForeground(Color.BLACK);
        prezzo.setSize(150, 100);
        JPanel pannello_prezzo = new JPanel();
        pannello_prezzo.setBackground(Color.white);
        pannello_prezzo.add(prezzo);
        pannello_prezzo.setBounds(55, 30, 300, 200);
        contenitore.add(pannello_prezzo, 5, 0);

        JLabel descrizione = new JLabel(desc);
        descrizione.setEnabled(false);
        JPanel pannello_descrizione = new JPanel();
        pannello_descrizione.setBackground(Color.white);
        pannello_descrizione.add(descrizione);
        //pannello_descrizione.setBackground(Color.BLACK);
        pannello_descrizione.setBounds(90, 60, 250, 100);
        contenitore.add(pannello_descrizione, 8, 0);

        contenitore.setSize(700, 700);
        return contenitore;
    }

    public static int calcolo_soldi() throws SQLException {
        try {
            Nuova_spesa.testConnection_spese();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet tmp = statement.executeQuery("SELECT * FROM spese");

        while (tmp.next()) {
            soldi = tmp.getInt("Importo") + soldi;
        }

    return soldi;
    }

    public static void main(String[] args) {
        new Andamento();
    }
}
