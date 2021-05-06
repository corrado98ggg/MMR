package it.mmr.accesso;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;

public class Termini extends JFrame {

    public Termini()
    {
        super("Termini e condizioni d'uso");

        JTextArea text=new JTextArea("L'eseguibile creato da : Corrado \nCarriero e Enrico Garrapa,E' un \nprogetto per l'esame di program\nmazione ad oggetti le risorse\nmulimediali e i dati usati sono\ndi proprietà intellettuale di UniMore");

        text.setFont(new Font("Monaco", Font.PLAIN, 15));
        JPanel t=new JPanel();
        t.setBackground(Color.white);
        t.add(text);
        setBounds(800,300,300,200);
        setContentPane(t);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Termini();
    }
}
