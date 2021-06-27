package it.mmr.layout.Tabs_divisione;

import it.mmr.Icon.Creazione_immagini;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Aiuto extends JFrame{

    JPanel area;
    public JLayeredPane aiuto() throws IOException {
        JLayeredPane a=new JLayeredPane();

        area = new JPanel();
        area.setSize(1585,985);
        area.setBackground(Color.WHITE);
        area.setLayout(new BoxLayout(area, BoxLayout.Y_AXIS));

        BufferedImage resized_schema = Creazione_immagini.creazioneImmagini("src/main/java/images/ddd.png", 1000, 600);
        area.add(leggi_file("src/main/java/images/1.txt"));

        JLabel schema=new JLabel(new ImageIcon(resized_schema));
        JPanel pannello_schema =new JPanel();
        pannello_schema.add(schema);
        pannello_schema.setBounds(200,200,1000,600);
        pannello_schema.setBackground(Color.WHITE);
        area.add(pannello_schema);

        JPanel text2=new JPanel();
        text2.setBackground(Color.WHITE);
        text2.add(leggi_file("src/main/java/images/aiuto.txt"));
        text2.setBounds(0,780,1400,985);
        area.add(text2,2,2);

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize(1535,985);
        a.add(scrollPane,1,0);
        return a;
    }

    public static JTextArea leggi_file(String a) throws IOException {
         String testo_aiuto;
        JTextArea area_testo;
        testo_aiuto = "";
        area_testo = new JTextArea();

        BufferedReader reader = new BufferedReader(new FileReader(a));


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
        area_testo.setBounds(0, 0,1585,985);
        area_testo.setFont(new Font("Monaco", Font.ITALIC, 20));
        return  area_testo;
    }

    public static void main(String[] args) throws IOException {
        new Aiuto();
    }
}