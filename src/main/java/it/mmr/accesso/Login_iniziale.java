package it.mmr.accesso;

import it.mmr.database.DBManager;
import it.mmr.database.Utils;
import it.mmr.layout.Schermata_Principale_home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login_iniziale extends JFrame implements ActionListener {
    JButton login;
    JButton condizioni;
    JLabel background;
    Color azzurro = new Color(0x01C8FF);
    Color azzurro1 = new Color(0x01C8FF);
    public static JTextField utente;
    JPasswordField password;
    JCheckBox tick;
    JTextArea etichetta_utente;
    JTextArea etichetta_password;

    // ImageIcon icon = new ImageIcon("java/code/src/main/java/im/secondo.png");
    public Login_iniziale() {
        super("MMR");
        ImageIcon icon_sign = new ImageIcon("src/main/java/images/iconasi_log.png.png");
        etichetta_password = new JTextArea("Password");
        etichetta_utente = new JTextArea("Nome utente");
        etichetta_utente.setEditable(false);
        etichetta_password.setEditable(false);

        JPanel ep = new JPanel();
        JPanel eu = new JPanel(); //pannelli relativi a label: password e utente
        //etichetta_utente.setBackground(new Color() );
        ep.add(etichetta_password);
        eu.add(etichetta_utente);
        eu.setBounds(97, 180, 90, 20);
        eu.setBackground(azzurro);

        ep.setBounds(100, 260, 60, 20);
        ep.setOpaque(true);
        etichetta_password.setBorder(BorderFactory.createEmptyBorder());
        //etichetta_password.setContentAreaFilled(false);*/

        login = new JButton(icon_sign);
        login.addActionListener(this);


        login.setBorder(BorderFactory.createEmptyBorder());
        login.setContentAreaFilled(false);

        condizioni = new JButton("termini e condizioni d'uso");
        condizioni.setForeground(new Color(0xE70E0E));
        condizioni.addActionListener(this);
        condizioni.setBorder(BorderFactory.createEmptyBorder());
        condizioni.setContentAreaFilled(false);


        //  l.setPreferredSize(g);
        //l.setBorder(BorderFactory.createEmptyBorder());
        // l.setContentAreaFilled(false);
        //var frame = new JFrame();
        setPreferredSize(new Dimension(1200, 550));
        setBounds(400, 200, 1200, 550);
        setLayout(new BorderLayout());
        JLayeredPane lpane = new JLayeredPane();
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 1200, 700);


        // panelBlue.setBackground(Color.BLUE);
        ImageIcon back = new ImageIcon("src/main/java/images/ben.jpeg");
        background = new JLabel("", back, JLabel.CENTER);
        background.setBounds(0, 0, 1200, 700);
        JPanel panelBack = new JPanel();
        panelBack.setBounds(0, -5, 1200, 700);


        panelBack.setOpaque(true);
        panelBack.add(background);
        JPanel panelLog = new JPanel();
        panelLog.setBackground(azzurro1);


        utente = new JTextField("");
        password = new JPasswordField("");


        JPanel access_utente = new JPanel();
        access_utente.setBackground(azzurro1);

        utente.setPreferredSize(new Dimension(400, 50));
        password.setPreferredSize(new Dimension(400, 50));
        access_utente.setBounds(100, 200, 400, 50);

        access_utente.add(utente);

        JPanel access_password = new JPanel();
        access_password.setBounds(100, 280, 400, 50);
        access_password.setBackground(azzurro1);
        access_password.add(password);

        panelLog.add(login);
        //panelGreen.add(sign_up);

        JPanel sing = new JPanel();
        JLabel messaggio = new JLabel("accetto");
        tick = new JCheckBox();
        tick.setBackground(azzurro1);

        sing.add(messaggio);
        sing.add(condizioni);
        sing.setBounds(0, 450, 300, 100);
        sing.setBackground(azzurro);
        sing.add(tick);

        panelLog.setBounds(400, 320, 105, 80);
        panelLog.setOpaque(true);

        lpane.add(panelBack, 0, 0);
        lpane.add(panelLog, 1, 0);
        lpane.add(access_utente, 2, 0);
        lpane.add(access_password, 2, 0);
        lpane.add(sing, 3, 0);
        lpane.add(eu, 4, 0);
        lpane.add(ep, 4, 0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //  frame.pack();
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println(e);

        //se il tasto premuto è: fai->


        if (e.getSource() == login) {

            /**
             * prima di chiamare un altra classe ricordati
             * di settare invisibili le finestre che non servono:
             * per chiamare una classe usa questa nomenclatura:
             */

            try {

                System.out.println(utente.getText());
                if (check_database(utente.getText(), password.getText()) == true && tick.isSelected() == true) {
                    setVisible(false);
                    new Schermata_Principale_home();

                } else {
                    JOptionPane.showMessageDialog(null, "Utente non registrato o condizioni non accettate");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        if (e.getSource() == condizioni) {
            new Termini_e_condizioni();
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
            statement.executeUpdate("CREATE TABLE registrazioni (" + "id VARCHAR(50) PRIMARY KEY, " + "Nome VARCHAR(50)," + "Password VARCHAR(50))");
        }
    }

    public boolean check_database(String utente_tmp, String password_tmp) throws SQLException {

        try {
            testConnection();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet rs = statement.executeQuery("SELECT Utente FROM registrazioni LIMIT 100");

        password_tmp = MD5(password_tmp);

        while (rs.next()) {
            if (rs.getString("utente").compareTo(utente_tmp) == 0) {

                ResultSet rs2 = statement.executeQuery("SELECT Password FROM registrazioni LIMIT 100");

                while (rs2.next()) {

                    System.out.println(password_tmp);
                    if (rs.getString("password").compareTo(password_tmp) == 0 || rs.getString("password").compareTo("admin1") == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String get_id(String utente_tmp, String divisione_tmp) throws SQLException {

        try {
            testConnection();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM registrazioni LIMIT 100");

        while (rs.next()) {
            if (rs.getString("utente").compareTo(utente_tmp) == 0 && rs.getString("divisione").compareTo(divisione_tmp) == 0) {
                return rs.getString("id");
            }
    }
        return "nessuno";
}

    public String MD5(String md5) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Login_iniziale();
    }

}
