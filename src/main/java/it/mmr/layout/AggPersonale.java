package it.mmr.layout;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.mmr.layout.Home.*;

public class  AggPersonale extends JFrame implements ActionListener {

    JButton add;
    static String s = new String("cdhcdbn");
    JTextField personale;

    public AggPersonale() {
        setSize(400, 400);
        JPanel agg = new JPanel();
        agg.setSize(400, 400);
        add = new JButton("add");
        add.addActionListener(this);
        personale = new JTextField("rgvrfwjhfwv uhw u bwu bvubwubcuwbcu bwy bwb wq");

        agg.add(personale);
        agg.add(add);

        setContentPane(agg);
        setVisible(true);

    }

    public static void main(String[] args) {
        new AggPersonale();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            s = new String(personale.getText());
            System.out.println(s);
            JLabel text = new JLabel(s);
            Home.p1.add(text);

        }
    }
}
