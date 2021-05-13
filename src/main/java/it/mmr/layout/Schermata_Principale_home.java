package it.mmr.layout;

import it.mmr.database.Registrazione_database;
import it.mmr.layout.Divisioni.*;

import it.mmr.layout.Tabs_divisione.PannelloTotale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Schermata_Principale_home extends JFrame {

   /* public JButton piu;
    public JButton meno;*/
    JTabbedPane divisioni;
    JButton avvertenza;

    public Schermata_Principale_home() {
        super("Home");


        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");
        //ImageIcon blocco_note = new ImageIcon("src/main/java/images/blocco_note.jpg");

        BufferedImage blocconote = null;
        try {
            blocconote = ImageIO.read(new File("src/main/java/images/blocco_note.png"));
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

        String prova= new String("shajcbkjjaòvujkzxnfoòuk m,hvdb8oiwyhieh");
        JTextArea testo=new JTextArea(prova);


        testo.setFont(new Font("Monaco", Font.ITALIC, 15));
        testo.setEditable(false);

      JPanel d=new JPanel();
      d.setBounds(30,400,200,200);
      d.add(testo);

       // JPanel contenitore =new JPanel();

        avvertenza=new JButton("agg avvertenza");
        JPanel panello_avvertenza=new JPanel();
        panello_avvertenza.add(avvertenza);
        panello_avvertenza.setBounds(85,975,200,200);

        JLayeredPane a=new JLayeredPane();

        //JLabel b=new JLabel((Icon) resized_blocco_note);
        JLabel picLabel = new JLabel(new ImageIcon(resized_blocco_note));
        JPanel c=new JPanel();

        JLabel picLabel1 = new JLabel(new ImageIcon(resized_danger));
        JPanel panello_danger=new JPanel();

        panello_danger.add(picLabel1);
        panello_danger.setBounds(45,950,65,80);

        c.setBackground(new Color(237,237,237));
        c.add(picLabel);
        c.setBounds(16,330,300,300);

        divisioni.add(null, logo_mmr);
        divisioni.addTab("aereodinamica", Aereodinamica.aereodinamica());
        divisioni.addTab("Business", PannelloTotale.PannelloTotale());
        divisioni.addTab("motori", PannelloTotale.PannelloTotale());
        divisioni.addTab("Dinamica del veicolo", PannelloTotale.PannelloTotale());
        divisioni.addTab("Elettronica", PannelloTotale.PannelloTotale());
        divisioni.addTab("Powertrain", PannelloTotale.PannelloTotale());
        divisioni.addTab("Ricerca", PannelloTotale.PannelloTotale());

        divisioni.setSelectedIndex(1);

        divisioni.setSize(1920,1080);
        a.setBounds(0,0,1920,1080);
        a.add(divisioni,0,0);
        a.add(c,1,1);
        a.add(d,2,2);
        a.add(panello_avvertenza,2,2);
        a.add(panello_danger,2,1);

        setContentPane(a);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setVisible(true);
    }



    public static void main(String[] args) {
        new Schermata_Principale_home();
    }
}