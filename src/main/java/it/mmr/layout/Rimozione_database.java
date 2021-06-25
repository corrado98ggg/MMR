package it.mmr.layout;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.accesso.Login_iniziale;
import it.mmr.database.DBManager;
import it.mmr.layout.Tabs_divisione.Personale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * creazione di interfaccia
 * di sing up
 * con metodo di mascheramento doHashing per salvataggio di password
 */
public class Rimozione_database extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    public JButton Button_ok;
    private final JButton Button_exit;

    public static BufferedImage resized_logo_mmr;
    public static BufferedImage resized_logo_uni;
    JTextField text_nome_utente;
    JTextField text_divisione;

    String nome_utente;
    String divisione;

    String id;

    public Rimozione_database() {

        super("Rimozione del personale");

        JLayeredPane lsignup = new JLayeredPane();
        add(lsignup, BorderLayout.CENTER);
        lsignup.setBounds(0, 0, 700, 475);

        Button_ok = new JButton("OK");
        Button_ok.addActionListener(this);

        Button_exit = new JButton("Exit");
        Button_exit.addActionListener(this);

        JLabel testo_nome = new JLabel("nome utente da rimovuovere");
        text_nome_utente = new JTextField("");
        text_nome_utente.setPreferredSize(new Dimension(250, 30));
        JPanel nome = new JPanel((new GridBagLayout()));
        nome.setBackground(new Color(0x02cbff));
        nome.add(testo_nome);
        nome.setBounds(397, 185, 200, 20);

        JLabel testo_divi = new JLabel("inserisci divisione appartenente all'utente");
        JPanel div = new JPanel((new GridBagLayout()));
        div.add(testo_divi);
        div.setBounds(397, 185, 200, 20);
        text_divisione = new JTextField("");
        text_divisione.setPreferredSize(new Dimension(250, 30));

        lsignup.add(nome, 2, 0);
        lsignup.add(testo_divi, 2, 0);
        /*
              creazione dei pannelli per l'interfaccia
              grafica della rimozione del personale.
             */
        resized_logo_mmr = Creazione_immagini.creazioneImmagini("src/main/java/images/mmr_logo.jpg", 300, 400);
        resized_logo_uni = Creazione_immagini.creazioneImmagini("src/main/java/images/logo_uni.png", 600, 800);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(400, 200, 250, 500);

        JPanel panel_logo_mmr = new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(60, 255, 300, 175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);


        panelscritte.add(text_nome_utente);
        panelscritte.add(text_divisione);
        panelscritte.add(Button_ok);
        panelscritte.add(Button_exit);

        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        lsignup.add(panel_logo_mmr, 1, 0);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(650, 300, 700, 475);
        setResizable(false);

        setVisible(true);

            /*
              prova di accesso al database locale
             */
        try {
            Registrazione_database.testConnection();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
        //Personale.colonna_ruoli();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Button_ok) {

            divisione = text_divisione.getText();
            nome_utente = text_nome_utente.getText();

            try {
                id = Login_iniziale.get_id(nome_utente, divisione);
                System.out.println(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (id.compareTo("nessuno") == 0) {
                JOptionPane.showMessageDialog(null, "Non c'è nessuno che corrisponde");
            } else {
                try {
                    String query = String.format(
                            "DELETE FROM registrazioni WHERE id='%s'",
                            id);
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                try {
                    Personale x = new Personale();
                    x.Stampa_personale(Personale.Matrice_personale());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "rimozione avvenuta con successo!");

            }
        }

        if (e.getSource() == Button_exit) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Rimozione_database();
    }
}
