package it.mmr.accesso;

import it.mmr.database.Utils;
import it.mmr.database.DBManager;

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

/** creazione di interfaccia
 * di sing up
 * con metodo di mascheramento doHashing per salvataggio di password
 */

public class Sign_up extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JButton ok;
    private final JButton exit;
    private final JTextField utente;
    private final JPasswordField password;
    public static String root_check;
    UUID id;
    JTextField nome;
    JTextField cognome;
    JTextField divisione;
    JCheckBox tick_root;


    public Sign_up() {

        super("Aggiungi personale");

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

        BufferedImage resized_logo_mmr = getScaledDimension(logo_mmr, 300, 400);
        BufferedImage resized_logo_uni = getScaledDimension(logo_uni, 600, 800);



        ok = new JButton("OK");
       // ok.setForeground(new Color(0x02cbff));
        ok.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        utente = new JTextField("Inserisci nome utente");
        utente.setPreferredSize(new Dimension(250,30));
        password = new JPasswordField("inserisci una nuova password");
        password.setPreferredSize(new Dimension(250,30));

        nome=new JTextField("nome");
        nome.setPreferredSize(new Dimension(250,30));
        cognome =new JTextField("cognome");
        cognome.setPreferredSize(new Dimension(250,30));
        divisione=new JTextField("divisione");
        divisione.setPreferredSize(new Dimension(250,30));
/*
        JPanel pn=new JPanel();
        pn.add(nome);
        JPanel pc=new JPanel();
        pc.add(cognome);
        JPanel pd=new JPanel();*/
        JLabel root=new JLabel("root");
        root.setBackground(new Color(0x02cbff));
        tick_root=new JCheckBox();



        JPanel panel = new JPanel();
        panel.setBounds(0,0,700,875);
        panel.setBackground(new Color(0x02cbff));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));

        JPanel panelscritte=new JPanel();
        panelscritte.setBackground(new Color(0x02cbff));
        panelscritte.setBounds(400,200,250,400);

        JPanel panel_logo_mmr=new JPanel();
        panel_logo_mmr.setBackground(new Color(0x02cbff));
        panel_logo_mmr.setBounds(60,255,300,175);

        panel.add(piclabel2);
        panel_logo_mmr.add(picLabel);
        panelscritte.add(utente);
        panelscritte.add(password);
        panelscritte.add(nome);
        panelscritte.add(cognome);
        panelscritte.add(divisione);
        panelscritte.add(root);
        panelscritte.add(tick_root);
        panelscritte.add(ok);
        panelscritte.add(exit);

          lsignup.add(panel,0,0);
          lsignup.add(panelscritte,1,0);
          lsignup.add(panel_logo_mmr,1,0);
        //setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(650,300,700,475);
        setResizable(false);
        
        //setSize(700, 475); //lunghezza * altezza
        setVisible(true);

        try {
            testConnection();
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

        //se il tasto premuto è: fai->
        if (e.getSource() == exit) {
            //chiudo l'applicazione:
            System.exit(0);
        }
        if (e.getSource() == ok) {

            /**
             * sicurezza password check
             * criteri: la password
             * deve avere minimo 6 caratteri e un numero
             */
            if (password.getText().length() > 5 && thereisnumber()) {

                System.out.println("Nome utente salvato:");
                System.out.println(utente.getText());

                System.out.println("password prima di Hashing:");
                System.out.println(password.getPassword());

                System.out.println("password dopo di Hashing:");
                System.out.println(MD5(password.getText()));

                String password_hash = MD5(password.getText());

                id = java.util.UUID.randomUUID();


                if(tick_root.isSelected() == true){
                    root_check = "TRUE";
                } else {
                    root_check = "FALSE";
                }


                try {
                    String query = String.format(
                            "INSERT INTO registrazioni (id, utente, nome, cognome, password, root, divisione) VALUES ('%s','%s', '%s', '%s', '%s', '%s', '%s')",
                            id, utente.getText(), nome.getText(), cognome.getText(), password_hash, root_check, divisione.getText() );
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
                JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo, ora puoi fare il login!");
                setVisible(false);

            } else {
                UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
                JOptionPane.showMessageDialog(null, "PASSWORD NON SICURA! Inserisci minimo 6 caratteri e un numero:");
            }
        }
    }

    public BufferedImage getScaledDimension(BufferedImage img, int MAX_IMG_WIDTH, int MAX_IMG_HEIGHT) {

        img.getWidth();
        int width;
        var height = img.getHeight();

        Dimension originalDimension = new Dimension(img.getWidth(),
                img.getHeight());
        Dimension boundaryDimension = new Dimension(MAX_IMG_WIDTH,
                MAX_IMG_HEIGHT);
        Dimension scalingDimension = getScaledDimension(originalDimension,
                boundaryDimension);

        width = (int) scalingDimension.getWidth();
        height = (int) scalingDimension.getHeight();

        BufferedImage resizedImage = new BufferedImage(width, height,
                img.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(img, 0, 0, width, height, null);
        return resizedImage;
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // Controllo se e' necessario eseguire lo scaling
        if (original_width > bound_width) {
            //scaling della larghezza in base alla larghezza massima
            new_width = bound_width;
            //eseguo lo scaling dell'altezza per mantenere le proporzioni
            new_height = (new_width * original_height) / original_width;
        }

        // Dopo aver calcolato la nuova altezza, controllo se è ancora fuori limite
        if (new_height > bound_height) {

            new_height = bound_height;
            //rieseguo lo scaling per mantenere le proporzioni
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
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
            statement.executeUpdate("CREATE TABLE registrazioni (" + "id VARCHAR(50) PRIMARY KEY, " + "Utente VARCHAR(50),"
                    + "Nome VARCHAR(50)," + "Cognome VARCHAR(50),"
                    + "Password VARCHAR(50)," + "Root BOOLEAN,"
                    + "Divisione VARCHAR(50))");
        }
    }

    /**
     *
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
*implementazione hashing di password tipo
*MD5
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
        new Sign_up();
    }

}

