package it.mmr.layout.Tabs_divisione;

import it.mmr.Icon.Creazione_immagini;
import it.mmr.accesso.Login_iniziale;
import it.mmr.database.DBManager;
import it.mmr.layout.Registrazione_database;
import it.mmr.layout.Rimozione_database;
import it.mmr.database.Utils;
import it.mmr.layout.Schermata_Principale_home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class Personale extends JFrame implements ActionListener, TableModelListener {

    private static int contatore_persone;
    public static String nome;
    public static String cognome;
    public static Object ruoli_modificato;
    public static String[][] dati;
    public static String[] nomi = {"nome",
            "cognome", "ruolo", "divisione"};

    public static JLayeredPane pannello_del_personale;
    public static BufferedImage resized_icon_piu;
    public static BufferedImage resized_icon_meno;
    public JButton piu, meno;

    public JLayeredPane personale() throws SQLException {

        pannello_del_personale = new JLayeredPane();

        resized_icon_meno = Creazione_immagini.creazioneImmagini("src/main/java/images/meno.png", 109, 109);
        resized_icon_piu = Creazione_immagini.creazioneImmagini("src/main/java/images/piuu.png", 100, 100);

        meno = new JButton(new ImageIcon(resized_icon_meno));
        meno.addActionListener(this);
        meno.setBorder(BorderFactory.createEmptyBorder());
        meno.setContentAreaFilled(false);
        JPanel pmeno = new JPanel();
        pmeno.setOpaque(true);
        pmeno.add(meno);
        pmeno.setBounds(1400, 750, 150, 150);

        piu = new JButton(new ImageIcon(resized_icon_piu));
        piu.addActionListener(this);
        piu.setBorder(BorderFactory.createEmptyBorder());
        piu.setContentAreaFilled(false);

        JPanel pannello_piu = new JPanel();
        pannello_piu.setSize(700, 700);
        pannello_piu.add(piu);
        pannello_piu.setOpaque(true);
        pannello_piu.setBounds(1400, 850, 150, 150);
        if (Login_iniziale.root) {
            pannello_del_personale.add(pannello_piu, 3, 0);
            pannello_del_personale.add(pmeno, 2, 0);
        }
        JPanel colore = new JPanel();
        colore.setSize(1920, 1200);
        pannello_del_personale.add(colore, 0, 0);

        Personale obj_personale = new Personale();
        obj_personale.Stampa_personale(Personale.Matrice_personale());

        JLabel testa_nome = new JLabel("nome");
        JLabel testa_cognome = new JLabel("cognome");
        JLabel testa_ruolo = new JLabel("ruolo");
        JLabel testa_divisione = new JLabel("divisione");

        JPanel colonna_nome = new JPanel();
        colonna_nome.add(testa_nome);
        colonna_nome.setBounds(0, 0, 50, 25);
        JPanel colonna_cognome = new JPanel();
        colonna_cognome.add(testa_cognome);
        colonna_cognome.setBounds(360, 0, 70, 25);
        JPanel colonna_ruolo = new JPanel();
        colonna_ruolo.add(testa_ruolo);
        colonna_ruolo.setBounds(700, 0, 50, 25);
        JPanel colonna_divisione = new JPanel();
        colonna_divisione.add(testa_divisione);
        colonna_divisione.setBounds(1050, 0, 70, 25);
        JPanel sfondo = new JPanel();
        sfondo.setBounds(0, 40, 1920, 1000);
        sfondo.setOpaque(false);

        pannello_del_personale.add(sfondo, 0, 0);
        pannello_del_personale.add(colonna_cognome, 0, 0);
        pannello_del_personale.add(colonna_divisione, 0, 0);
        pannello_del_personale.add(colonna_ruolo,0 , 0);
        pannello_del_personale.add(colonna_nome, 0, 0);
        return pannello_del_personale;
    }

    public static String[][] Matrice_personale() throws SQLException {

        try {
            contatore_persone = Utils.quante_persone_sono_registrate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dati = new String[contatore_persone][4];
        int i = 0;

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM registrazioni LIMIT 100");
        int k = 0;
        while (queryPersonale.next()) {
            if (i >= contatore_persone) {
                continue;
            }
            for (int j = 1; j < 5; j++) {

                //caso base:
                if (i == 0 && j == 1) {
                    dati[i][j - 1] = queryPersonale.getString("nome");
                    System.out.println(dati[i][j - 1]);

                    continue;
                }

                if (j == 4) {
                    dati[i][j - 1] = queryPersonale.getString("Divisione");
                    System.out.println(dati[i][j - 1]);
                    continue;
                }

                if (j / 2 == 1) {
                    if (k == 1) {
                        dati[i][j - 1] = queryPersonale.getString("ruoli");
                        System.out.println(dati[i][j - 1]);
                        continue;
                    }
                    dati[i][j - 1] = queryPersonale.getString("cognome");
                    System.out.println(dati[i][j - 1]);
                    k++;
                    continue;
                }

                dati[i][j - 1] = queryPersonale.getString("nome");
                System.out.println(dati[i][j - 1]);

            }
            k = 0;
            i++;
        }
        return dati;
    }

    public void Stampa_personale(String[][] tmp) {

        JTable table = new JTable(dati, nomi);
        table.getModel().addTableModelListener(this);
        table.setRowHeight(35);
        table.getColumn("nome").setCellEditor(new TableMy(new JCheckBox()));
        table.getColumn("cognome").setCellEditor(new TableMy(new JCheckBox()));
        table.getColumn("divisione").setCellEditor(new TableMy(new JCheckBox()));
        //table.setEditingColumn(1);
        table.setBounds(0, 25, 1400, 1420);
        if(!Login_iniziale.root)
        {
            table.getColumn("ruolo").setCellEditor(new TableMy(new JCheckBox()));
        }
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setBounds(0, 0, 1400, 985);
        pannello_del_personale.add(scrollPane, 1, 0);
        // return table;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == piu) {
            new Registrazione_database();
        }
        if (e.getSource() == meno) {
            new Rimozione_database();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {

        int row = e.getFirstRow();
        //System.out.println(row);
        int column = e.getColumn();
        //System.out.println(column);
        TableModel model = (TableModel) e.getSource();
        //System.out.println(model);
        //System.out.println(columnName);
        ruoli_modificato = model.getValueAt(row, column);
        //System.out.println(ruoli_modificato);

        nome = dati[row][0];
        cognome = dati[row][1];

        try {
            Schermata_Principale_home.aggiungi_ruolo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}