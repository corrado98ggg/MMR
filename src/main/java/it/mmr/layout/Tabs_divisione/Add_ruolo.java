package it.mmr.layout.Tabs_divisione;

import it.mmr.layout.Schermata_Principale_home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Add_ruolo extends JFrame implements ActionListener {

    public  JButton ok;
    public JTextArea str;
    public String stringa_di_ruolo;

    public Add_ruolo(){

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

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            stringa_di_ruolo = str.getText();


            try {
                Schermata_Principale_home.aggiungi_ruolo(stringa_di_ruolo);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                Schermata_Principale_home.check_ruolo();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
