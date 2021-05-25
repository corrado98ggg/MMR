package it.mmr.layout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Avvertenze extends JFrame implements ActionListener {
    JButton ok;
    String stringa_di_avvertenza;
    JTextArea str;

    public Avvertenze() {
        setSize(200, 200);
        JPanel pannello_avvertenze = new JPanel();
        str = new JTextArea();
        pannello_avvertenze.add(str);
        ok = new JButton("ok");
        ok.addActionListener(this);
        pannello_avvertenze.add(ok);
        setContentPane(pannello_avvertenze);
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
