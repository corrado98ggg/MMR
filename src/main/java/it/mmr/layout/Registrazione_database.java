package it.mmr.layout;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.database.DBManager;
import it.mmr.database.Utils;
import it.mmr.layout.Tabs_divisione.Personale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * creazione di interfaccia
 * di sing up
 * con metodo di mascheramento doHashing per salvataggio di password
 */

public class Registrazione_database extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JButton ok;
    private final JButton exit;
    private final JPasswordField password;
    public static String root_check;
    UUID id;
    JTextField nome;
    JTextField cognome;
    JTextField divisione;
    JCheckBox tick_root;

    public static BufferedImage resized_logo_mmr;
    public static BufferedImage resized_logo_uni;


    public Registrazione_database() {

        super("Aggiungi personale");

        JLayeredPane lsignup = new JLayeredPane();
        add(lsignup, BorderLayout.CENTER);
        lsignup.setBounds(0, 0, 700, 475);

        resized_logo_mmr = Creazione_immagini.creazioneImmagini("src/main/java/images/mmr_logo.jpg", 300, 400);
        resized_logo_uni = Creazione_immagini.creazioneImmagini("src/main/java/images/logo_uni.png", 600, 800);

        ok = new JButton("OK");
        ok.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);

        password = new JPasswordField("");
        password.setPreferredSize(new Dimension(250, 30));
        nome = new JTextField("");
        nome.setPreferredSize(new Dimension(250, 30));
        cognome = new JTextField("");
        cognome.setPreferredSize(new Dimension(250, 30));
        divisione = new JTextField("");
        divisione.setPreferredSize(new Dimension(250, 30));

        JLabel root = new JLabel("root");
        root.setBackground(new Color(0x02cbff));
        tick_root = new JCheckBox();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 875);
        panel.setBackground(new Color(0x02cbff));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));

        JPanel panelscritte = new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(475, 220, 250, 400);

        JPanel panel_logo_mmr = new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(5, 230, 300, 175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);

        JLabel testo_nome=new JLabel("nome:");
        JPanel pannello_nome=new JPanel(new GridBagLayout());
        pannello_nome.setBackground(new Color(0,0,0,0));
        pannello_nome.add(testo_nome);
        pannello_nome.setBounds(320,235,135,20);
        lsignup.add(pannello_nome,1,1);

        JLabel testo_cognome=new JLabel("cognome:");
        JPanel pannello_cognome =new JPanel(new GridBagLayout());
        pannello_cognome.setBackground(new Color(0,0,0,0));
        pannello_cognome.add(testo_cognome);
        pannello_cognome.setBounds(320,270,135,20);
        lsignup.add(pannello_cognome,1,1);

        JLabel testo_password =new JLabel("password:");
        JPanel pannello_scritta1=new JPanel(new GridBagLayout());
        pannello_scritta1.setBackground(new Color(0,0,0,0));
        pannello_scritta1.add(testo_password);
        pannello_scritta1.setBounds(320,305,135,20);
        lsignup.add(pannello_scritta1,1,1);

        JLabel testo_divisione =new JLabel("divisione:");
        JPanel pannello_scritta2=new JPanel(new GridBagLayout());
        pannello_scritta2.setBackground(new Color(0,0,0,0));
        pannello_scritta2.add(testo_divisione);
        pannello_scritta2.setBounds(320,340,135,20);
        lsignup.add(pannello_scritta2,1,1);

        panelscritte.add(nome);
        panelscritte.add(cognome);
        panelscritte.add(password);
        panelscritte.add(divisione);
        panelscritte.add(root);
        panelscritte.add(tick_root);
        panelscritte.add(ok);
        panelscritte.add(exit);

        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        lsignup.add(panel_logo_mmr, 1, 0);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(650, 300, 750, 475);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        try {
            testConnection();
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

        //se il tasto premuto è: fai->
        if (e.getSource() == exit) {
            setVisible(false);
        }
        if (e.getSource() == ok) {

            /*
              sicurezza password check
              criteri: la password
              deve avere minimo 6 caratteri e un numero
             */
            if (password.getText().length() > 5 && thereisnumber()) {

                //System.out.println("Nome utente salvato:");

                System.out.println("password prima di Hashing:");
                System.out.println(password.getPassword());

                System.out.println("password dopo di Hashing:");
                System.out.println(MD5(password.getText()));

                String password_hash = MD5(password.getText());

                id = java.util.UUID.randomUUID();

                if (tick_root.isSelected()) {
                    root_check = "TRUE";
                } else {
                    root_check = "FALSE";
                }

                try {
                    String query = String.format(
                            "INSERT INTO registrazioni (id, nome, cognome, password, root, divisione) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                            id, nome.getText(), cognome.getText(), password_hash, root_check, divisione.getText());
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
                JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo, ora puoi fare il login!");

                try {
                    Personale x = new Personale();
                    x.Stampa_personale(Personale.Matrice_personale());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                setVisible(false);

            } else {
                UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
                JOptionPane.showMessageDialog(null, "PASSWORD NON SICURA! Inserisci minimo 6 caratteri e un numero:");
            }
        }
    }

    public static void testConnection() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver_SQLite,
                Utils.JDBC_URL_SQLite);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM registrazioni");
        } catch (SQLException e) {
            statement.executeUpdate("DROP TABLE IF EXISTS registrazioni");
            statement.executeUpdate("CREATE TABLE registrazioni (" + "id VARCHAR(50) PRIMARY KEY, "
                    + "Nome VARCHAR(50)," + "Cognome VARCHAR(50),"
                    + "Password VARCHAR(50)," + "Root BOOLEAN,"
                    + "Ruoli LONGVARCHAR,"
                    + "Divisione VARCHAR(50))");
        }
    }


    /**
     * @return true o false
     * controllo se c'è un numero
     */
    private boolean thereisnumber() {

        int end = password.getText().length();
        char[] src_new = password.getText().toCharArray();

        System.out.println(src_new);

        int[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        for (int i = 0; i < end; i++) {

            for (int j = 0; j < 10; j++) {
                if ((src_new[i] - 48) == number[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * implementazione hashing di password tipo
     * MD5
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return null;
    }


    public static void main(String[] args) {
        new Registrazione_database();
    }
}