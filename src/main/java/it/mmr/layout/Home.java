package it.mmr.layout;

import it.mmr.accesso.Sign_up;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Home extends JFrame implements ActionListener {
    JButton piu, piu_bussiness, piu_motori, piu_dinamica_del_veicolo, piu_powertrain, piu_ricerca;
    public static JPanel p1;
    public JMenuBar barra;

    public Home() {
        super("Home");

        //primo menu:
        JMenuItem personale = new JMenuItem("Personale");

        ImageIcon icon_piu = new ImageIcon("src/main/java/images/piu.png");
        ImageIcon logo_mmr = new ImageIcon("src/main/java/images/mmr_logo.jpg");

        piu = new JButton(icon_piu);

        piu_bussiness = new JButton(icon_piu);
        piu_motori = new JButton(icon_piu);
        piu_dinamica_del_veicolo = new JButton(icon_piu);
        piu_powertrain = new JButton(icon_piu);
        piu_ricerca = new JButton(icon_piu);

        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);
        piu.addActionListener(this);
        JPanel a = new JPanel();
        a.setSize(700, 700);

        a.add(piu);

        a.setBounds(1370, 850, 150, 150);
        JLayeredPane p1 = new JLayeredPane();
        p1.add(a, 1, 0);

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

        //SwingUtilities.invokeLater(new CalendarFrame());
        // calle.add(calendari);

        JPanel p2_1 = new JPanel();
        JPanel p3_1 = new JPanel();
        JPanel p4_1 = new JPanel();
        JPanel p5_1 = new JPanel();
        JPanel p6_1 = new JPanel();


        attività.add(null, logo_mmr);

        p2_1.add(piu_bussiness);
        p2_1.setBounds(1370, 850, 150, 150);
        piu_bussiness.setBorder(BorderFactory.createEmptyBorder());
        piu_bussiness.setContentAreaFilled(false);
        piu_bussiness.addActionListener(this);

        p3_1.add(piu_motori);
        p3_1.setBounds(1370, 850, 150, 150);
        piu_motori.setBorder(BorderFactory.createEmptyBorder());
        piu_motori.setContentAreaFilled(false);
        piu_motori.addActionListener(this);

        p4_1.add(piu_powertrain);
        p4_1.setBounds(1370, 850, 150, 150);
        piu_powertrain.setBorder(BorderFactory.createEmptyBorder());
        piu_powertrain.setContentAreaFilled(false);
        piu_powertrain.addActionListener(this);

        p5_1.add(piu_ricerca);
        p5_1.setBounds(1370, 850, 150, 150);
        piu_ricerca.setBorder(BorderFactory.createEmptyBorder());
        piu_ricerca.setContentAreaFilled(false);
        piu_ricerca.addActionListener(this);

        p6_1.add(piu_dinamica_del_veicolo);
        p6_1.setBounds(1370, 850, 150, 150);
        piu_dinamica_del_veicolo.setBorder(BorderFactory.createEmptyBorder());
        piu_dinamica_del_veicolo.setContentAreaFilled(false);
        piu_dinamica_del_veicolo.addActionListener(this);


        attività.addTab("aereodinamica", tabs);
        attività.addTab("Business", p2_1);
        attività.addTab("motori", p3_1);
        attività.addTab("Dinamica del veicolo", p4_1);
        attività.addTab("Elettronica", p5_1);
        attività.addTab("Powertrain", p6_1);
        attività.addTab("Ricerca", p4_1);

        attività.setSelectedIndex(1);


        setContentPane(attività);
        setSize(1920, 1080);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * aggiunta di un nuovo account
         * che può accedere all'eseguibile
         */
        if (e.getSource() == piu) {
            new Sign_up();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}