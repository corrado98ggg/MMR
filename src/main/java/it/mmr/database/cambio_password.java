package it.mmr.database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class cambio_password extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    public static JPasswordField password_vecchia;
    public static JPasswordField password_nuova_1;
    public static JPasswordField password_nuova_2;


    public static JTextField nome;
    public static JTextField cognome;

    public static JButton ok;
    public static JButton exit;

    public cambio_password() {

        super("Cambia password");

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

        nome = new JTextField("nome");
        nome.setPreferredSize(new Dimension(250, 30));

        cognome = new JTextField("cognome");
        cognome.setPreferredSize(new Dimension(250, 30));

        password_vecchia = new JPasswordField("Inserisci la vecchia password");
        password_vecchia.setPreferredSize(new Dimension(250, 30));

        password_nuova_1 = new JPasswordField("new password");
        password_nuova_1.setPreferredSize(new Dimension(250, 30));

        password_nuova_2 = new JPasswordField("new password");
        password_nuova_2.setPreferredSize(new Dimension(250, 30));

        ok = new JButton("OK");
        ok.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);


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


        panelscritte.add(nome);
        panelscritte.add(cognome);
        panelscritte.add(password_vecchia);
        panelscritte.add(password_nuova_1);
        panelscritte.add(password_nuova_2);
        panelscritte.add(exit);
        panelscritte.add(ok);
        lsignup.add(panel, 0, 0);
        lsignup.add(panelscritte, 1, 0);
        lsignup.add(panel_logo_mmr, 1, 0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(650, 300, 700, 475);
        setResizable(false);
        setVisible(true);

        try {
            Registrazione_database.testConnection();
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



        if (e.getSource() == exit) {
            setVisible(false);
        }

        if (e.getSource() == ok) {

            System.out.println(password_nuova_1.getPassword());
            System.out.println(password_vecchia.getPassword());

            try {
                cambio_nel_database();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void cambio_nel_database() throws SQLException {

        String password_hash_vecchia;
        String password_hash_nuova;

        password_hash_nuova = Registrazione_database.MD5(password_nuova_1.getText()); //da andare nel databse e al check sostituire
        password_hash_vecchia = Registrazione_database.MD5(password_vecchia.getText());

        if (!password_nuova_1.getText().equals(password_nuova_2.getText())) {
            UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
            JOptionPane.showMessageDialog(null, "Le due password non corrispondono!");
            setVisible(false);
        } else {


            Statement statement_tmp = DBManager.getConnection().createStatement();
            ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM Registrazioni LIMIT 100");

            while (queryPersonale.next()) {
                if (nome.getText().equals(queryPersonale.getString("nome"))) {
                    if (cognome.getText().equals(queryPersonale.getString("cognome"))) {
                        assert password_hash_vecchia != null;
                        if(password_hash_vecchia.equals(queryPersonale.getString("password"))){
                            try {
                                String query = String.format(
                                        "UPDATE Registrazioni SET password=('%s') WHERE Nome IS ('%s') AND Cognome IS ('%s');",
                                        password_hash_nuova, nome.getText(), cognome.getText());
                                Statement statement_2 = DBManager.getConnection().createStatement();
                                statement_2.executeUpdate(query);
                                statement_2.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
            UIManager.put("OptionPane.minimumSize", new Dimension(100, 90));
            JOptionPane.showMessageDialog(null, "password cambiata con successo!");

        }

    }

    public static void main(String[] args) {
        new cambio_password();
    }
}

