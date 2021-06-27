package it.mmr.layout.Tabs_divisione;

import it.mmr.Icon.Creazione_immagini;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Aiuto extends JFrame{

    public static String testo_aiuto;
    JTextArea area_testo;
    JLayeredPane area;

    public JLayeredPane aiuto() throws IOException {

        area = new JLayeredPane();
        testo_aiuto = "";
        area_testo = new JTextArea();

        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/images/aiuto.txt"));

        String line = reader.readLine();
        while(line!=null) {
            line = reader.readLine();
            if(line != null) {
                testo_aiuto = testo_aiuto + '\n' + line;
            }
        }

        area_testo.setText(testo_aiuto);
        area_testo.setLineWrap(true);
        area_testo.setWrapStyleWord(true);
        area_testo.setEditable(false);
        area_testo.setBounds(0, 0,1620,1400);
        area_testo.setFont(new Font("Monaco", Font.ITALIC, 20));

        area.add(area_testo,0,0);
        area.setBounds(0,0,1620,1400);

        return area;
    }

    public static void main(String[] args) throws IOException {
        new Aiuto();
    }
}