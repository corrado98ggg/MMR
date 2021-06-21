package it.mmr.layout.Tabs_divisione;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Aiuto extends JFrame{

    public static String testo_aiuto;
    Console Input;
    JTextArea area_testo;
    JLayeredPane area;

    public JLayeredPane Aiuto() throws IOException {

        area = new JLayeredPane();
        testo_aiuto = new String();
        area_testo = new JTextArea();

        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/images/aiuto.txt"));

        String line = reader.readLine();
        while(line!=null) {
            //System.out.println(line);
            line = reader.readLine();

            testo_aiuto = testo_aiuto + line;
        }
        area_testo.setText(testo_aiuto);
        area_testo.setLineWrap(true);
        area_testo.setSize(1520,1200);

        area_testo.setFont(new Font("Monaco", Font.ITALIC, 20));
        area.add(area_testo,0,0);
        area.setBounds(0,0,1520,1200);

       // setSize(1920,1080);
        return area;
    }

    public static void main(String[] args) throws IOException {
        new Aiuto();
    }
}