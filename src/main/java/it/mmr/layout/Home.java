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

public class Home extends JFrame {
    public Home() {
        super("Home");

        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();

        JTabbedPane tabs=new JTabbedPane(JTabbedPane.LEFT);


        tabs.addTab("aererodinamica",p1);
        tabs.addTab("busness",p2);
        tabs.addTab("motori",p3);

        setContentPane(tabs);
        setSize(1920,1080);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Home();
    }
}