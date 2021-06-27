package it.mmr.accesso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Messaggio extends JFrame implements ActionListener {
    JButton ok;

    public Messaggio() {
        JLayeredPane contenitore = new JLayeredPane();

        JTextArea text = new JTextArea("Utente non è registrato o condizioni non accettate");
        text.setEditable(false);
        text.setFont(new Font("MONACO", Font.ITALIC, 17));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(true);
        text.setBackground(Color.CYAN);
        text.setSize(275, 200);
        JPanel panello_text = new JPanel(new GridBagLayout());
        panello_text.setBackground(new Color(0, 0, 0, 0));
        panello_text.add(text);
        panello_text.setBounds(200, 50, 270, 80);
        ok = new JButton("ok");
        ok.addActionListener(this);
        JPanel pannello_ok = new JPanel(new GridBagLayout());
        pannello_ok.setBackground(new Color(0, 0, 0, 0));
        pannello_ok.add(ok);
        pannello_ok.setBounds(285, 150, 60, 50);
        JLabel gif = new JLabel(new ImageIcon("src/main/java/images/no!.gif"));
        JPanel pannello_gif = new JPanel();
        pannello_gif.add(gif);
        pannello_gif.setBounds(0, -50, 200, 250);
        JPanel sfondo = new JPanel();
        sfondo.setBackground(Color.CYAN);
        sfondo.setSize(420, 400);
        contenitore.add(sfondo, 0, 0);
        contenitore.add(panello_text, 2, 0);
        contenitore.add(pannello_ok, 3, 1);
        contenitore.add(pannello_gif, 1, 1);
        setBounds(700,400,410, 238);
        contenitore.setBounds(500, 500, 410, 238);
        setContentPane(contenitore);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
         setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Messaggio();
    }
}
