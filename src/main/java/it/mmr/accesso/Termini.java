package it.mmr.accesso;

import javax.swing.*;
import java.awt.*;

public class Termini extends JFrame {

    public Termini()
    {
        super("Termini e condizioni d'uso");

        JTextArea text=new JTextArea("L'eseguibile creato da : \nCorrado Carriero e\nEnrico Garrapa,\nE' un progetto per l'esame\n di programmazione ad oggetti\nle risorse mulimediali e i dati usati\nsono di proprietà intellettuale di UniMore");

        text.setSize(300, 400);
        JPanel t=new JPanel();
        t.setBackground(Color.white);
        t.add(text);
        setBounds(800,300,300,200);
        setContentPane(t);
        setResizable(false);
        setVisible(true);
    }
}
