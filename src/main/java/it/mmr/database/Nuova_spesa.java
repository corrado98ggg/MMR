package it.mmr.database;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.layout.Tabs_divisione.Andamento;
import it.mmr.layout.Tabs_divisione.Spese;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * creazione di interfaccia
 * di sing up
 * con metodo di mascheramento doHashing per salvataggio di password
 */

public class Nuova_spesa extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    public JButton ok;
    public static JButton quit;
    UUID id;
    public Integer quantit;
    public Integer prezz;
    public Integer impor;
    JTextField descrizione;
    JTextField qt;
    JTextField prezzo_al_pezzo;
    JTextField importo;
    public static BufferedImage resized_logo_mmr;
    public static BufferedImage resized_logo_uni;
    public static int soldi = 0;
  public static   int i = 3;

    public Nuova_spesa() throws SQLException {

        super("Nuova Spesa");

        JLayeredPane lsignup = new JLayeredPane();
        add(lsignup, BorderLayout.CENTER);
        lsignup.setBounds(0, 0, 700, 475);

        resized_logo_mmr = Creazione_immagini.creazioneImmagini("src/main/java/images/mmr_logo.jpg", 300, 400);
        resized_logo_uni = Creazione_immagini.creazioneImmagini("src/main/java/images/logo_uni.png", 600, 800);

        ok = new JButton("OK");
        ok.addActionListener(this);
        quit = new JButton("Exit");
        quit.addActionListener(this);

        descrizione = new JTextField("");
        descrizione.setPreferredSize(new Dimension(250, 30));
        qt = new JTextField("");
        qt.setPreferredSize(new Dimension(250, 30));
        prezzo_al_pezzo = new JTextField("");
        prezzo_al_pezzo.setPreferredSize(new Dimension(250, 30));
        importo = new JTextField("");
        importo.setPreferredSize(new Dimension(250, 30));

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(475, 200, 250, 400);

        JPanel panel_logo_mmr = new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(5, 230, 300, 175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);

        JLabel testo_descrizione =new JLabel("descrizione:");
        JPanel pannello_descrizione =new JPanel(new GridBagLayout());
        pannello_descrizione.setBackground(new Color(0,0,0,0));
        pannello_descrizione.add(testo_descrizione);
        pannello_descrizione.setBounds(320,210,135,20);
        lsignup.add(pannello_descrizione,1,1);

        JLabel testo_quantità =new JLabel("quantita':");
        JPanel pannello_quantità =new JPanel(new GridBagLayout());
        pannello_quantità.setBackground(new Color(0,0,0,0));
        pannello_quantità.add(testo_quantità);
        pannello_quantità.setBounds(320,250,135,20);
        lsignup.add(pannello_quantità,1,1);

        JLabel testo_unita =new JLabel("prezzo ad' unita':");
        JPanel pannello_unita =new JPanel(new GridBagLayout());
        pannello_unita.setBackground(new Color(0,0,0,0));
        pannello_unita.add(testo_unita);
        pannello_unita.setBounds(320,285,135,20);
        lsignup.add(pannello_unita,1,1);

        JLabel testo_import =new JLabel("importo:");
        JPanel pannello_import =new JPanel(new GridBagLayout());
        pannello_import.setBackground(new Color(0,0,0,0));
        pannello_import.add(testo_import);
        pannello_import.setBounds(320,320,135,20);
        lsignup.add(pannello_import,1,1);

        panelscritte.add(descrizione);
        panelscritte.add(qt);
        panelscritte.add(prezzo_al_pezzo);
        panelscritte.add(importo);
        panelscritte.add(ok);
        panelscritte.add(quit);

        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        lsignup.add(panel_logo_mmr, 1, 0);
        //setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(650, 300, 750, 475);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //setSize(700, 475); //lunghezza * altezza
        setVisible(true);

        try {
            testConnection_spese();
            //load();
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

        if (e.getSource() == quit){
            setVisible(false);
        }

        if (e.getSource() == ok) {

            quantit = Integer.valueOf(qt.getText());
            //System.out.println(quantit);
            prezz = Integer.valueOf(prezzo_al_pezzo.getText());
            //System.out.println(prezz);
            impor = Integer.valueOf(importo.getText());
            //System.out.println(impor);

            id = java.util.UUID.randomUUID();

            try {
                String query = String.format(
                        "INSERT INTO Spese (id, Descrizione, Quantità, Prezzo_unit, Importo) VALUES ('%s', '%s', '%d', '%d', '%d')",
                        id, descrizione.getText(), quantit, prezz, impor);
                Statement statement = DBManager.getConnection().createStatement();
                statement.executeUpdate(query);
                statement.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
            JOptionPane.showMessageDialog(null, "Spesa registrata con successo!");

            try {
                Spese obj_spese = new Spese();
                obj_spese.Stampa_spese(Spese.Matrice_spese());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            JLayeredPane a = null;
            try {
                a = Andamento.indice(Andamento.icona2, "Spese", calcolo_soldi(), "Documenti ingresso anno corrente", Color.red);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert a != null;
            a.setBounds(0, 400, 400, 400);
            a.setBackground(Color.white);
            Andamento.tot.add(a, i, 0);
            i++;

            try {
                Andamento.tot.add(Andamento.indice(Andamento.icona4, "Totale", 10000 - calcolo_soldi(), "Soldi rimanenti", Color.green), i, 0);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            setVisible(false);
        }
    }


    public static int calcolo_soldi() throws SQLException {
        try {
            Nuova_spesa.testConnection_spese();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        soldi = 0;

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet tmp = statement.executeQuery("SELECT * FROM spese");

        while (tmp.next()) {
            soldi = tmp.getInt("Importo") + soldi;
        }

        return soldi;
    }

    public static void testConnection_spese() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver_SQLite,
                Utils.JDBC_URL_SQLite);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM spese");
        } catch (SQLException e) {
            statement.executeUpdate("DROP TABLE IF EXISTS spese");
            statement.executeUpdate("CREATE TABLE spese (" + "id VARCHAR(50) PRIMARY KEY, "
                    + "Descrizione VARCHAR(50)," + "Quantità INTEGER,"
                    + "Prezzo_unit INTEGER," + "Importo INTEGER)");
        }
    }

    public static void main(String[] args) {
        try {
            new Nuova_spesa();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}