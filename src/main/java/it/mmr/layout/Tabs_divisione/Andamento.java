package it.mmr.layout.Tabs_divisione;

import it.mmr.database.Registrazione_database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Andamento extends JFrame {


    private static JLabel prezzo;
    public static JPanel sfondo;

    String s;
    int i = 29900;

    public static JLayeredPane Andamento() throws SQLException {

        JLayeredPane tot = new JLayeredPane();
        tot.setBounds(0, 0, 200, 200);

        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("src/main/java/images/crediti.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage icon2 = null;
        try {
            icon2 = ImageIO.read(new File("src/main/java/images/acquisti.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage icon3 = null;
        try {
            icon3 = ImageIO.read(new File("src/main/java/images/debito.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage icon4 = null;
        try {
            icon4 = ImageIO.read(new File("src/main/java/images/fondo_cassa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage icona = Registrazione_database.getScaledDimension(icon, 60, 60);
        BufferedImage icona2 = Registrazione_database.getScaledDimension(icon2, 95, 95);
        BufferedImage icona3 = Registrazione_database.getScaledDimension(icon3, 60, 60);
        BufferedImage icona4 = Registrazione_database.getScaledDimension(icon4, 95, 95);

        tot.add(indice(icona4, "Capitale", 678998, "Soldi rimanenti", Color.green), 1, 0);
        JLayeredPane a = indice(icona2, "Acquisti", 34567, "Documenti in ingresso anno corrente", Color.red);
        JLayeredPane b = indice(icona3, "Debito", 345678, "Debiti al 12/10/18", Color.red);
        JLayeredPane c = indice(icona, "crediti", 759202, "documenti in ingresso anno correte", Color.green);

        sfondo = new JPanel();
        sfondo.setBackground(Color.white);
        sfondo.setBounds(0,0,1920,1080);


        b.setBounds(0, 600, 400, 400);
        b.setBackground(Color.white);
        a.setBounds(0, 400, 400, 400);
        a.setBackground(Color.white);
        c.setBounds(0, 200, 400, 400);
        c.setBackground(Color.white);

        tot.add(sfondo, 0,0);
        tot.add(a, 3, 0);
        tot.add(c,2,0);
        tot.add(b,4,0);

        Spese pane=new Spese();
        JLayeredPane tab=pane.Spese();
        tab.setBounds(300,0,1000,1000);
        tot.add(tab,2,0);
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


        prezzo = new JLabel(String.valueOf(h) + " €");
        // prezzo.setEnabled(false);
        prezzo.setFont(new Font("Monaco", Font.BOLD, 20));
        prezzo.setForeground(Color.BLACK);
        prezzo.setSize(150, 100);
        JPanel pannello_prezzo = new JPanel();
        pannello_prezzo.setBackground(Color.white);
        pannello_prezzo.add(prezzo);
        pannello_prezzo.setBounds(55, 30, 300, 200);
        contenitore.add(pannello_prezzo, 5, 0);

        ;
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

    public static void main(String[] args) {
        new Andamento();
    }
}
