package it.mmr.layout;

import javax.swing.*;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener{
    JButton piu;
   public static JPanel p1;
    public Home() {
        super("Home");

         p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();

         piu=new JButton("piu");
         piu.addActionListener(this);
        p1.add(piu);
        String a=new String(AggPersonale.s);
        System.out.println(a);

        JLabel pro=new JLabel(a);

        JTabbedPane tabs=new JTabbedPane(JTabbedPane.LEFT);


        tabs.addTab("aererodinamica",p1);
        tabs.addTab("busness",p2);
        tabs.addTab("motori",p3);

        setContentPane(tabs);
        setSize(1920,1080);

        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);

        if(e.getSource()==piu){

           new AggPersonale();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}