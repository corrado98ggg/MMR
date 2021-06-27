package it.mmr.accesso;

import javax.swing.*;
import java.awt.*;

public class Termini_e_condizioni extends JFrame {

    public Termini_e_condizioni()
    {
        super("Termini e condizioni d'uso");

        JTextArea text=new JTextArea("""
                L'eseguibile creato da : Corrado
                Carriero e Enrico Garrapa,
                è un progetto per l'esame di\040
                programmazione ad oggetti. Le risorse
                multimediali e i dati usati sono
                di proprietà intellettuale di UniMore.""");

        text.setFont(new Font("Monaco", Font.ITALIC, 20));
        text.setEditable(false);
        JPanel t=new JPanel();
        t.setBackground(Color.white);
        t.add(text);
        setBounds(800,300,450,200);
        setContentPane(t);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Termini_e_condizioni();
    }
}
