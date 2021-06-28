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


        text_nome_utente = new JTextField("");
        text_nome_utente.setPreferredSize(new Dimension(250, 30));




        text_divisione = new JTextField("");
        text_divisione.setPreferredSize(new Dimension(250, 30));

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
        panelscritte.setBounds(430, 250, 250, 500);

        JPanel panel_logo_mmr = new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(5, 235, 300, 175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);

        JLabel testo_nome=new JLabel("nome:");
        JPanel pannello_nome=new JPanel(new GridBagLayout());
        pannello_nome.setBackground(new Color(0,0,0,0));
        pannello_nome.add(testo_nome);
        pannello_nome.setBounds(320,260,135,20);
        lsignup.add(pannello_nome,1,1);

        JLabel testo_divisione =new JLabel("divisione:");
        JPanel pannello_scritta2=new JPanel(new GridBagLayout());
        pannello_scritta2.setBackground(new Color(0,0,0,0));
        pannello_scritta2.add(testo_divisione);
        pannello_scritta2.setBounds(320,300,135,20);
        lsignup.add(pannello_scritta2,1,1);

        panelscritte.add(text_nome_utente);
        panelscritte.add(text_divisione);
        panelscritte.add(Button_ok);
        panelscritte.add(Button_exit);

        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        lsignup.add(panel_logo_mmr, 1, 0);

        setBounds(675, 300, 700, 475);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
