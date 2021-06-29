package it.mmr.database;

import it.mmr.layout.Registrazione_database;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {
    public static final String JDBC_Driver_SQLite = "org.sqlite.JDBC";
    public static final String JDBC_URL_SQLite = String.format("jdbc:sqlite:%s",
            Paths.get(Utils.mmrdir(), "mmr.sqlite"));

    public static String mmrdir() {
        String path = String.format("%s%s%s%s%s", System.getProperty("user.home"), System.getProperty("file.separator"),
                "Scrivania", System.getProperty("file.separator"), "mmr");
        new File(path).mkdirs();
        return path;
    }

    public static int quante_persone_sono_registrate() throws SQLException {
        int cont = 0;

        try {
            Registrazione_database.testConnection();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM registrazioni LIMIT 100");

        while (rs.next()) {
            cont++;
        }
        return cont;
    }

    public static int quante_spese_sono_registrate() throws SQLException {
        int cont = 0;

        try {
            Registrazione_database.testConnection();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Spese LIMIT 100");

        while (rs.next()) {
            cont++;
        }
        return cont;
    }
}