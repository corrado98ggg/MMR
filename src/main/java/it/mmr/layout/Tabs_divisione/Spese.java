package it.mmr.layout.Tabs_divisione;

import it.mmr.database.DBManager;
import it.mmr.database.Nuova_spesa;
import it.mmr.database.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Spese extends JFrame implements ActionListener, TableModelListener {

    public static String[][] dati_spese;
    public static int row = -1;
    public static int column = -1;
    public static JLayeredPane pannello_della_tabella;
    public static JTable table;
    String tmp;


    public static String[] nomi = {"Descrizione",
            "Quantità", "Prezzo a unità", "Importo"};
    public JButton piu, meno;
    public static int contatore_spese;
    public JLayeredPane spese() throws SQLException {

        pannello_della_tabella = new JLayeredPane();
        pannello_della_tabella.setSize(300, 300);

        piu = new JButton(new ImageIcon(Personale.resized_icon_piu));
        piu.addActionListener(this);
        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);
        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setBounds(1400, 850, 150, 150);

        meno = new JButton(new ImageIcon(Personale.resized_icon_meno));
        meno.addActionListener(this);
        meno.setBorder(BorderFactory.createEmptyBorder());
        meno.setContentAreaFilled(false);
        JPanel pannello_meno = new JPanel();
        pannello_meno.setSize(700, 700);
        pannello_meno.add(meno);
        pannello_meno.setBounds(1400, 750, 150, 150);

        pannello_della_tabella.add(pannello_meno, 2, 0);
        pannello_della_tabella.add(pannello_piu, 2, 0);

        JPanel colore = new JPanel();
        colore.setSize(1920, 1200);
        pannello_della_tabella.add(colore, 0, 0);
        Personale tabella = new Personale();

        tabella.Stampa_personale(Personale.Matrice_personale());

        Spese obj_spese = new Spese();
        obj_spese.Stampa_spese(Spese.Matrice_spese());

        JPanel sfondo = new JPanel();
        sfondo.setBounds(0, 40, 1920, 1000);
        sfondo.setOpaque(false);
        pannello_della_tabella.add(sfondo, 0, 0);

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setBounds(0, 0, 1400, 985);
        pannello_della_tabella.add(scrollPane, 1, 0);

        return pannello_della_tabella;

    }

    public static String[][] Matrice_spese() throws SQLException {

        try {
            contatore_spese = Utils.quante_spese_sono_registrate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dati_spese = new String[contatore_spese][4];

        int i = 0;

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM spese");

        while (queryPersonale.next()) {
            if (i >= contatore_spese) {
                continue;
            }
            dati_spese[i][0] = queryPersonale.getString("Descrizione");
            //System.out.println(dati_spese[i][0]);

            dati_spese[i][1] = queryPersonale.getString("Quantità");
            //System.out.println(dati_spese[i][1]);

            dati_spese[i][2] = queryPersonale.getString("Prezzo_unit");
            //System.out.println(dati_spese[i][2]);

            dati_spese[i][3] = queryPersonale.getString("Importo");
            //System.out.println(dati_spese[i][3]);

            i++;
        }
        return dati_spese;
    }

    public void Stampa_spese(String[][] tmp) {

        table = new JTable(dati_spese, nomi);
        table.getModel().addTableModelListener(this);
        table.setRowHeight(35);
        table.getColumn("Descrizione").setCellEditor(new TableMy(new JCheckBox()));
        table.getColumn("Quantità").setCellEditor(new TableMy(new JCheckBox()));
        table.getColumn("Prezzo a unità").setCellEditor(new TableMy(new JCheckBox()));
        table.getColumn("Importo").setCellEditor(new TableMy(new JCheckBox()));
        table.setBounds(0, 25, 1400, 1420);

        Spese.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    row = Spese.table.getSelectedRow();
                    column = Spese.table.getSelectedColumn();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setBounds(0, 0, 1400, 985);
        pannello_della_tabella.add(scrollPane, 1, 0);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == piu) {
            try {
                new Nuova_spesa();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (e.getSource() == meno) {

            if (row == -1 && column == -1) {
                JOptionPane.showConfirmDialog(null, "Non è stata selezionata nessuna spesa, per favore seleziona una spesa");

            } else {

                int v = JOptionPane.showConfirmDialog(null, "La spesa selezionata verrà eliminata. vuoi procedere?");

                if (v == 0) {
                    try {
                        rimuovi_spesa();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * This fine grain notification tells listeners the exact range
     * of cells, rows, or columns that changed.
     *
     * @param e a {@code TableModelEvent} to notify listener that a table model
     *          has changed
     */
    @Override
    public void tableChanged(TableModelEvent e) { }

    public void rimuovi_spesa() throws SQLException {

        tmp = dati_spese[row][column];
        int number = 0;


        try {
            number = Integer.parseInt(tmp);
            System.out.println(number); // output = 25
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM spese LIMIT 100");


        while (queryPersonale.next()) {
            if (tmp.equals(queryPersonale.getString("Descrizione")) ||
                    tmp.equals(queryPersonale.getString("Quantità")) ||
                    tmp.equals(queryPersonale.getString("Prezzo_unit")) ||
                    tmp.equals(queryPersonale.getString("Importo"))) {

                try {
                    String query2 = String.format(
                            "DELETE FROM spese WHERE Quantità='%d' OR Prezzo_unit='%d' OR Importo='%d'",
                            number, number, number);
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query2);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                try {
                    String query_tuo = String.format(
                            "DELETE FROM spese WHERE Descrizione='%s'",
                            tmp);
                    Statement statement = DBManager.getConnection().createStatement();
                    statement.executeUpdate(query_tuo);
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Rimozione avvenuta con successo");
        Spese obj_spese = new Spese();
        obj_spese.Stampa_spese(Spese.Matrice_spese());
        JLayeredPane a = null;

        try {
            Andamento.tot.add(Andamento.indice(Andamento.icona4, "Totale", 10000 - Nuova_spesa.calcolo_soldi(), "Soldi rimanenti", Color.green), Nuova_spesa.i, 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            a = Andamento.indice(Andamento.icona2, "Spese", Nuova_spesa.calcolo_soldi(), "Documenti ingresso anno corrente", Color.red);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert a != null;
        a.setBounds(0, 400, 400, 400);
        a.setOpaque(false);
        Andamento.tot.add(a, Nuova_spesa.i, 0);
        Nuova_spesa.i++;
    }
}
