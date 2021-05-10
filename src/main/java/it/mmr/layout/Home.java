package it.mmr.layout;

import it.mmr.accesso.Sign_up;
import it.mmr.Funzioni_comuni.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static it.mmr.accesso.Sign_up.getScaledDimension;

public class Home extends JFrame implements ActionListener{
    JButton piu;
   public static JPanel p1;
    public Home() {
        super("Home");

       ImageIcon icon_piu= new ImageIcon("src/main/java/images/piu.png");
      /*
        BufferedImage logo_mmr = null;
        try {
            logo_mmr = ImageIO.read(new File("src/main/java/images/mmr_logo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert logo_mmr != null;

        BufferedImage resized_logo_mmr = Sign_up.getScaledDimension(logo_mmr, 50, 50);

        ImageIcon nome = new ImageIcon("src/main/java/images/mmr_logo.jpg");
        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
*/

        piu=new JButton(icon_piu);
        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);
        piu.addActionListener(this);
        JPanel a=new JPanel();
        a.setSize(700,700);
        a.add(piu);
        a.setBounds(1580,900,150,150);
        JLayeredPane p1=new JLayeredPane();
        p1.add(a,1,0);

        JPanel colore=new JPanel();
        colore.setSize(1920,1200);
        p1.add(colore,0,0);


        JTabbedPane prova=new JTabbedPane();
        JPanel calle=new JPanel();
        prova.addTab("Calendario",calle);


        p1.add(prova,2,0);

        JTabbedPane tabs = new JTabbedPane();
        JTabbedPane attività = new JTabbedPane(JTabbedPane.LEFT);

        tabs.setBackground(Color.CYAN);
        tabs.addTab("Personale",p1);
        tabs.addTab("calendario",calle);

       SwingUtilities.invokeLater(new CalendarFrame());
       // calle.add(calendari);

        JPanel p2_1=new JPanel();
        JPanel p3_1=new JPanel();
        JPanel p4_1=new JPanel();
        JPanel p5_1=new JPanel();
        JPanel p6_1=new JPanel();


        //attività.add(null, (Icon) picLabel);

        attività.addTab("aereodinamica",tabs);
        attività.addTab("Business",p2_1);
        attività.addTab("motori",p3_1);
        attività.addTab("Dinamica del veicolo", p4_1);
        attività.addTab("Elettronica",p5_1);
        attività.addTab("Powertrain",p6_1);
        attività.addTab("Ricerca", p4_1);


        setContentPane(attività);
        setSize(1920,1080);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * aggiunta di un nuovo account
         * che può accedere all'eseguibile
         */
        if(e.getSource()==piu){
           new Sign_up();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}