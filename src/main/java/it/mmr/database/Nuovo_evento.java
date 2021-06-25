package it.mmr.database;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.layout.Tabs_divisione.Eventii.Eventi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.sql.SQLException;
import java.sql.Statement;

public class Nuovo_evento extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JButton ok;
    private final JButton exit;

    public static JTextField mese;
    public static JTextField giorno;
    public static JTextField anno;
    public static JTextField evento;
    public static JTextField ora;
    public static BufferedImage resized_flag;

    public Nuovo_evento() {

        super("Aggiungi evento");

        JLayeredPane lsignup = new JLayeredPane();
        add(lsignup, BorderLayout.CENTER);
        lsignup.setBounds(0, 0, 700, 475);

        resized_flag = Creazione_immagini.creazioneImmagini("src/main/java/images/flag.png", 600, 800);

        ok = new JButton("OK");
        ok.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);

        mese = new JTextField("Inserisci mese");
        mese.setPreferredSize(new Dimension(250, 30));

        ora = new JTextField("Inserisci ora");
        ora.setPreferredSize(new Dimension(250, 30));

        giorno = new JTextField("Inserisci giorno");
        giorno.setPreferredSize(new Dimension(250, 30));

        anno = new JTextField("Inserisci anno");
        anno.setPreferredSize(new Dimension(250, 30));

        evento = new JTextField("Inserisci evento");
        evento.setPreferredSize(new Dimension(250, 30));

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel piclabel2 = new JLabel(new ImageIcon(resized_flag));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(400, 300, 250, 400);

        panel.add(piclabel2);
        panelscritte.add(mese);
        panelscritte.add(giorno);
        panelscritte.add(anno);
        panelscritte.add(ora);
        panelscritte.add(evento);
        panelscritte.add(ok);
        panelscritte.add(exit);

        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(650, 300, 700, 475);
        setResizable(false);

        setSize(700, 570); //lunghezza * altezza
        setVisible(true);

        try {
            Eventi.testConnection_Eventi();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

       String[] mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio",
                "Agosto", "Settembre", "Ottobre",
                "Novembre", "Dicembre", "gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio",
                "agosto", "settembre", "ottobre",
                "novembre", "dicembre"};

        String[] giorni = {String.valueOf(1), String.valueOf(2), String.valueOf(3), String.valueOf(4),
                String.valueOf(5), String.valueOf(7), String.valueOf(8), String.valueOf(9), String.valueOf(10), String.valueOf(11),
                String.valueOf(12), String.valueOf(13), String.valueOf(14), String.valueOf(15), String.valueOf(16), String.valueOf(17),
                String.valueOf(18), String.valueOf(19), String.valueOf(20), String.valueOf(21), String.valueOf(22), String.valueOf(23),
                String.valueOf(24), String.valueOf(25), String.valueOf(26), String.valueOf(27), String.valueOf(28), String.valueOf(29),
                String.valueOf(30), String.valueOf(31)};


        if (e.getSource() == exit) {
            setVisible(false);
        }
        if (e.getSource() == ok) {


            if((check_mesi(mesi, mese.getText())) && (check_giorni(giorni, giorno.getText()))) {

                //System.out.println(mese.getText());
                //System.out.println(giorno.getText());
                //System.out.println(anno.getText());
                //System.out.println(ora.getText());
                //System.out.println(evento.getText());
                try {
                    String query = String.format(
                            "INSERT INTO Eventi (evento, giorno, anno, ora, mese) VALUES ('%s', '%s', '%s', '%s', '%s')",
                            evento.getText(), giorno.getText(), anno.getText(), ora.getText(), mese.getText());
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
                JOptionPane.showMessageDialog(null, "Evento Registrato con successo!");
                setVisible(false);

            } else {

                UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
                JOptionPane.showMessageDialog(null, "Per favore controlla mese e/o giorno!");

            }
        }
    }

    boolean check_mesi(String[] mesi, String mese) {

        for(int i = 0; i < 24; i++){
            if(mesi[i].equals(mese)){
                return true;
            }
        }
        return false;
    }

    boolean check_giorni(String[] giorni, String giorno) {

        for(int i = 0; i < 31; i++){
            if(giorni[i].equals(giorno)){
                return true;
            }
        }
        return false;
    }
}
