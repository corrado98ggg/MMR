package it.mmr.layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Avvertenze extends JFrame implements ActionListener {
    JButton ok;
    String stringa_di_avvertenza;
    JTextField str;

    public Avvertenze() {
        JLayeredPane contenitore=new JLayeredPane();
        JPanel sfondo=new JPanel();
        sfondo.setBackground(Color.CYAN);
        sfondo.setBounds(0,0,500,200);
        contenitore.add(sfondo,0,0);
        contenitore.setSize(500,200);
        setBounds(500,500,500, 200);
        JPanel pannello_avvertenze = new JPanel(new GridBagLayout());
        pannello_avvertenze.setBackground(new Color(0,0,0,0));
        str = new JTextField("");
        str.setPreferredSize(new Dimension(400, 50));
       // str.setSize(150,50);
        pannello_avvertenze.add(str);
        pannello_avvertenze.setBounds(50,10,400,50);
        contenitore.add(pannello_avvertenze,1,0);

        JPanel pannello_ok=new JPanel(new GridBagLayout());
        pannello_ok.setBackground(new Color(0,0,0,0));
        ok = new JButton("ok");
        ok.addActionListener(this);
        pannello_ok.add(ok);
        pannello_ok.setBounds(225,100,60,40);
        contenitore.add(pannello_ok,2,0);

        setContentPane(contenitore);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Avvertenze();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            stringa_di_avvertenza = str.getText();

            try {
                Schermata_Principale_home.aggiungi_stringa(stringa_di_avvertenza);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                Schermata_Principale_home.a.add(Schermata_Principale_home.check_avvertenza(), Schermata_Principale_home.i, 2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Schermata_Principale_home.i++;
        }
    }
}
