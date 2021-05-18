package it.mmr.database;

import javax.swing.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Utils {
    public static final String JDBC_Driver_SQLite = "org.sqlite.JDBC";
    public static final String JDBC_URL_SQLite = String.format("jdbc:sqlite:%s",
            Paths.get(Utils.mmrdir(), "mmr.sqlite").toString());

    //public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    //public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/jdbc_schema?user=nicola&password=qwertyuio&serverTimezone=" +
    //       TimeZone.getDefault().getID();

    public static String mmrdir() {
        String path = String.format("%s%s%s%s%s", System.getProperty("user.home"), System.getProperty("file.separator"),
                "Scrivania", System.getProperty("file.separator"), "mmr");
        new File(path).mkdirs();
        return path;
    }

    public static UUID asUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static int quante_persone_sono_registrate() throws SQLException {
        int cont = 0;

        try {
            Registrazione_database.testConnection();
            //load();
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
}