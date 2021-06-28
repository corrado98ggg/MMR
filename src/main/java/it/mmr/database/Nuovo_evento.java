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

        mese = new JTextField("");
        mese.setPreferredSize(new Dimension(250, 30));

        ora = new JTextField("");
        ora.setPreferredSize(new Dimension(250, 30));

        giorno = new JTextField("");
        giorno.setPreferredSize(new Dimension(250, 30));

        anno = new JTextField("");
        anno.setPreferredSize(new Dimension(250, 30));

        evento = new JTextField("");
        evento.setPreferredSize(new Dimension(250, 30));
        JPanel sfondo=new JPanel();
        sfondo.setSize( 800, 875);
        sfondo.setBackground(new Color(0x02cbff));
        JPanel panel = new JPanel();
        panel.setBounds(-170, 0, 800, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel piclabel2 = new JLabel(new ImageIcon(resized_flag));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(400, 300, 250, 400);

        JLabel testo_descrizion =new JLabel("mese:");
        JPanel pannello_descrizion =new JPanel(new GridBagLayout());
        pannello_descrizion.setBackground(new Color(0,0,0,0));
        pannello_descrizion.add(testo_descrizion);
        pannello_descrizion.setBounds(270,310,135,20);
        lsignup.add(pannello_descrizion,2,1);

        JLabel testo_descrizione =new JLabel("giorno:");
        JPanel pannello_descrizione =new JPanel(new GridBagLayout());
        pannello_descrizione.setBackground(new Color(0,0,0,0));
        pannello_descrizione.add(testo_descrizione);
        pannello_descrizione.setBounds(270,350,135,20);
        lsignup.add(pannello_descrizione,2,1);

        JLabel testo_quantità =new JLabel("anno:");
        JPanel pannello_quantità =new JPanel(new GridBagLayout());
        pannello_quantità.setBackground(new Color(0,0,0,0));
        pannello_quantità.add(testo_quantità);
        pannello_quantità.setBounds(270,390,135,20);
        lsignup.add(pannello_quantità,2,1);

        JLabel testo_unita =new JLabel("ora:");
        JPanel pannello_unita =new JPanel(new GridBagLayout());
        pannello_unita.setBackground(new Color(0,0,0,0));
        pannello_unita.add(testo_unita);
        pannello_unita.setBounds(270,425,135,20);
        lsignup.add(pannello_unita,2,1);

        JLabel testo_import =new JLabel("evento:");
        JPanel pannello_import =new JPanel(new GridBagLayout());
        pannello_import.setBackground(new Color(0,0,0,0));
        pannello_import.add(testo_import);
        pannello_import.setBounds(270,460,135,20);
        lsignup.add(pannello_import,2,1);


        panel.add(piclabel2);
        panelscritte.add(mese);
        panelscritte.add(giorno);
        panelscritte.add(anno);
        panelscritte.add(ora);
        panelscritte.add(evento);
        panelscritte.add(ok);
        panelscritte.add(exit);
        lsignup.add(sfondo, 0, 0);
        lsignup.add(panel, 1, 0);
        lsignup.add(panelscritte, 2, 0);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(650, 300, 700, 475);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
    public static void main(String[] args) {
new Nuovo_evento();
    }
}


