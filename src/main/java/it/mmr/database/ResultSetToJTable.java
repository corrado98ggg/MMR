package it.mmr.database;

import it.mmr.database.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetToJTable extends JFrame {

    public ResultSetToJTable() {
        super("ResultSetToJTable");

        try {
            testConnection();
            setContentPane(new JScrollPane(getTable("SELECT * FROM registrazioni")));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }

        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void testConnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver_SQLite, Utils.JDBC_URL_SQLite);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM registrazioni");
        } catch (SQLException e) {
            statement.executeUpdate("DROP TABLE IF EXISTS registrazioni");
            statement.executeUpdate("CREATE TABLE registrazioni (" + "id VARCHAR(50) PRIMARY KEY, " + "Nome VARCHAR(50)" + "Password VARCHAR(50))");
        }
    }

    public JTable getTable(String query) throws SQLException {
        JTable t = new JTable();
        DefaultTableModel dm = new DefaultTableModel();

        ResultSet rs = DBManager.getConnection().createStatement().executeQuery(query);
        ResultSetMetaData rsMetaData = rs.getMetaData();

        // get columns metadata
        int cols = rsMetaData.getColumnCount();
        String[] c = new String[cols];
        for (int i = 0; i < cols; i++) {
            c[i] = rsMetaData.getColumnName(i + 1);
            dm.addColumn(c[i]);
        }

        // Get rows
        Object[] row = new Object[cols];
        while (rs.next()) {
            for (int i = 0; i < cols; i++) {
                row[i] = rs.getString(i + 1);
            }
            dm.addRow(row);
        }

        t.setModel(dm);
        t.setGridColor(Color.BLACK);
        return t;
    }
}