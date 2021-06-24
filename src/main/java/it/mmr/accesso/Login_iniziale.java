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
    public static boolean isopen = false;
    public static JTextField utente;
    JPasswordField password;
    JCheckBox tick;

    public static boolean root = false;

    public Login_iniziale() {
        super("MMR");
        ImageIcon icon_sign = new ImageIcon("src/main/java/images/iconasi_log.png.png");

        login = new JButton(icon_sign);
        login.addActionListener(this);
        login.setBorder(BorderFactory.createEmptyBorder());
        login.setContentAreaFilled(false);

        condizioni = new JButton("termini e condizioni d'uso");
        condizioni.setForeground(new Color(0xE70E0E));
        condizioni.addActionListener(this);
        condizioni.setBorder(BorderFactory.createEmptyBorder());
        condizioni.setContentAreaFilled(false);

        setPreferredSize(new Dimension(1200, 550));
        setBounds(400, 200, 1200, 550);
        setLayout(new BorderLayout());
        JLayeredPane lpane = new JLayeredPane();
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 1200, 700);

        ImageIcon back = new ImageIcon("src/main/java/images/back.jpg");
        background = new JLabel("", back, JLabel.CENTER);
        background.setBounds(0, 0, 1200, 700);
        JPanel panelBack = new JPanel();
        panelBack.setBounds(0, -5, 1200, 700);
        panelBack.setOpaque(true);
        panelBack.add(background);
        JPanel panelLog = new JPanel(new GridBagLayout());
        panelLog.setBackground(new Color(0,0,0,0));
       // panelLog.setBackground(azzurro1);

        utente = new JTextField("");
        password = new JPasswordField("");

        JPanel access_utente = new JPanel(new GridBagLayout());
        access_utente.setBackground(new Color(0,0,0,0));

        utente.setPreferredSize(new Dimension(400, 50));
        password.setPreferredSize(new Dimension(400, 50));
        access_utente.setBounds(50, 230, 400, 50);

        access_utente.add(utente);

        JPanel access_password = new JPanel(new GridBagLayout());
        access_password.setBounds(50, 380, 400, 50);
        access_password.setBackground(new Color(0,0,0,0));
        access_password.add(password);

        panelLog.add(login);
        JPanel sing = new JPanel(new GridBagLayout());
        JLabel messaggio = new JLabel("accetto");
        tick = new JCheckBox();
        tick.setBackground(new Color(0,0,0,0));

        sing.add(messaggio);
        sing.add(condizioni);
        sing.setBounds(0, 450, 300, 100);
        sing.setBackground(new Color(0,0,0,0));
        sing.add(tick);

        panelLog.setBounds(490, 360, 105, 80);
        panelLog.setOpaque(true);

        lpane.add(panelBack, 0, 0);
        lpane.add(panelLog, 1, 0);
        lpane.add(access_utente, 2, 0);
        lpane.add(access_password, 2, 0);
        lpane.add(sing, 3, 0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {
            try {

                System.out.println(utente.getText());
                if (check_database(utente.getText(), password.getText()) && tick.isSelected()) {
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
            if(!isopen) {

                new Termini_e_condizioni(); // non è fixato da me
                isopen=true;
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

        ResultSet rs = statement.executeQuery("SELECT * FROM registrazioni LIMIT 100");

        password_tmp = MD5(password_tmp);

        while (rs.next()) {
            if (rs.getString("nome").compareTo(utente_tmp) == 0) {

                ResultSet rs2 = statement.executeQuery("SELECT Password FROM registrazioni LIMIT 100");

                while (rs2.next()) {

                    System.out.println(password_tmp);
                    if (rs.getString("password").compareTo(password_tmp) == 0 || rs.getString("password").compareTo("admin1") == 0) {


                        try {
                            String query = String.format(
                                    "SELECT root FROM registrazioni WHERE Nome IS ('%s') AND password IS ('%s');",
                                    utente_tmp, password_tmp);
                            System.out.println(query);

                            ResultSet rs3 = statement.executeQuery(query);
                            System.out.println(rs3);

                            Statement statement_2 = DBManager.getConnection().createStatement();

                            if(rs.getString("root").compareTo("TRUE") == 0){
                                root = true;
                            }
                            statement_2.executeUpdate(query);
                            statement_2.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

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
