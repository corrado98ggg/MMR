package it.mmr.database;

import it.mmr.layout.Tabs_divisione.Spese;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
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


    public Nuova_spesa() {

        super("Nuova Spesa");

        JLayeredPane lsignup = new JLayeredPane();
        add(lsignup, BorderLayout.CENTER);
        lsignup.setBounds(0, 0, 700, 475);


        BufferedImage logo_uni = null;
        try {
            logo_uni = ImageIO.read(new File("src/main/java/images/logo_uni.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage logo_mmr = null;
        try {
            logo_mmr = ImageIO.read(new File("src/main/java/images/mmr_logo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert logo_mmr != null;
        assert logo_uni != null;

        BufferedImage resized_logo_mmr = Registrazione_database.getScaledDimension(logo_mmr, 300, 400);
        BufferedImage resized_logo_uni = Registrazione_database.getScaledDimension(logo_uni, 600, 800);


        ok = new JButton("OK");
        // ok.setForeground(new Color(0x02cbff));
        ok.addActionListener(this);
        quit = new JButton("Exit");
        quit.addActionListener(this);


        descrizione = new JTextField("descrizione");
        descrizione.setPreferredSize(new Dimension(250, 30));
        qt = new JTextField("quantità");
        qt.setPreferredSize(new Dimension(250, 30));
        prezzo_al_pezzo = new JTextField("prezzo all'unità");
        prezzo_al_pezzo.setPreferredSize(new Dimension(250, 30));
        importo = new JTextField("Importo");
        importo.setPreferredSize(new Dimension(250, 30));

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(400, 200, 250, 400);

        JPanel panel_logo_mmr = new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(60, 255, 300, 175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);

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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(650, 300, 700, 475);
        setResizable(false);

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
            System.out.println(quantit);
            prezz = Integer.valueOf(prezzo_al_pezzo.getText());
            System.out.println(prezz);
            impor = Integer.valueOf(importo.getText());
            System.out.println(impor);


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
                Spese x = new Spese();
                x.Stampa_spese(Spese.Matrice_spese());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            setVisible(false);
        }
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
        new Nuova_spesa();
    }

}

