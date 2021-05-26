package it.mmr.layout.Tabs_divisione;


import it.mmr.database.DBManager;
import it.mmr.database.Nuovo_evento;
import it.mmr.database.Registrazione_database;
import it.mmr.database.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class Eventi extends JFrame implements ActionListener {

    public static JLayeredPane contenitore;

    public static JPanel contenitore_eventi;
    public JButton piu;

    public static JTextArea eventi;
    public static String etichetta_evento;

    public static String etichetta_fissa;
    public static JTextArea etichetta_fissa_area;

    // public Component Eventi;

    public JLayeredPane Eventi() throws SQLException {
        contenitore = new JLayeredPane();
        Calendario calendario = new Calendario();
        contenitore.add(calendario.Calendario(), 1, 0);


        BufferedImage icon_piu = null;
        try {
            icon_piu = ImageIO.read(new File("src/main/java/images/piuu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage resized_icon_piu = Registrazione_database.getScaledDimension(icon_piu, 100, 100);

        piu = new JButton(new ImageIcon(resized_icon_piu));
        piu.addActionListener(this);

        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);

        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setBackground(Color.white);
        pannello_piu.setBounds(1430, 850, 150, 150);

        //etichetta_fissa = new String(Calendario.fisso);
        //etichetta_evento = new String(Calendario.event);


        //piu = new JButton("piu");
        // tasto_piu=new JPanel();
        // tasto_piu.add(piu);
        contenitore.add(pannello_piu, 4, 0);

        testConnection_Eventi();


        Disegno_evento(Calendario.fisso, Calendario.event);
/*
        etichetta_fissa_area = new JTextArea(etichetta_fissa);
        eventi = new JTextArea(etichetta_evento);
        contenitore_eventi = new JPanel();

       // eventi.setForeground(new Color(0xE70E0E));
        contenitore_eventi.setBackground(Color.yellow);
       // contenitore_eventi.setBackground(Color.white);
        contenitore_eventi.add(eventi);
        contenitore_eventi.add(etichetta_fissa_area);
        contenitore_eventi.setBounds(-200, 500, 1900, 30);
        eventi.setOpaque(false);

        eventi.setSize(1500,50);
        eventi.setLineWrap(true);
        contenitore.add(contenitore_eventi, 2, 0);
        // contenitore.setBounds(0,700,200,200);

        eventi.setEnabled(false);*/

        JPanel sfondo=new JPanel();
        sfondo.setBackground(Color.white);
        sfondo.setBounds(0,0,1920,1080);
        contenitore.add(sfondo,0,0);


        JPanel Titolo=new JPanel();
        Titolo.setBackground(Color.white);
        JLabel text=new JLabel("Eventi:");
        text.setFont(new Font("Monaco", Font.ITALIC, 40));
        text.setForeground(Color.red);
        Titolo.add(text);
        Titolo.setBounds(-5,420,200,60);
        contenitore.add(Titolo,1,0);

        return contenitore;
    }

    public static void testConnection_Eventi() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver_SQLite,
                Utils.JDBC_URL_SQLite);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Eventi");
        } catch (SQLException e) {
            statement.executeUpdate("DROP TABLE IF EXISTS Eventi");
            statement.executeUpdate("CREATE TABLE Eventi (" + "Evento LONGVARCHAR PRIMARY KEY, "
                    + "Giorno INTEGER," + "Anno INTEGER,"
                    + "Ora VARCHAR(50),"
                    + "Mese VARCHAR(50))");
        }
    }


    public static void Disegno_evento(String fisso, String evento) throws SQLException{

        etichetta_fissa_area = new JTextArea(evento);
        eventi = new JTextArea(fisso);
        eventi.setForeground(Color.red);
        contenitore_eventi = new JPanel();

        // eventi.setForeground(new Color(0xE70E0E));
        contenitore_eventi.setBackground(Color.yellow);
        // contenitore_eventi.setBackground(Color.white);
        contenitore_eventi.add(eventi);
        contenitore_eventi.add(etichetta_fissa_area);
        contenitore_eventi.setBounds(-200, 500, 1900, 30);
        eventi.setOpaque(false);

        eventi.setSize(1500,50);
        eventi.setLineWrap(true);
        contenitore.add(contenitore_eventi, 2, 0);

        JPanel contenitore_fisso = new JPanel();
        contenitore_fisso.add(etichetta_fissa_area);
        contenitore_fisso.setBackground(Color.white);
        contenitore_fisso.setBounds(-200, 700, 1900, 200);
        etichetta_fissa_area.setSize(1500,200);
        etichetta_fissa_area.setLineWrap(true);
        etichetta_fissa_area.setOpaque(false);
        contenitore.add(contenitore_fisso, 3, 0);
        // contenitore.setBounds(0,700,200,200);

        eventi.setEnabled(false);


    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == piu) {
            new Nuovo_evento();
        }
    }
}