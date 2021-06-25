package it.mmr.layout.Tabs_divisione.Eventii;

import it.mmr.database.DBManager;
import it.mmr.layout.Registrazione_database;
import it.mmr.layout.Tabs_divisione.TableMy;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Calendario extends JFrame {
    static JLabel etichettaMese;
    static JButton bottonePreview, bottoneProssimo;
    public static String[] stringa;
    static JTable tabellacalendario;
    static JComboBox<String> ComboboxAnno;

    static DefaultTableModel ModelloTabellaCalendario; //Table model
    static JScrollPane scroll; //The scrollpane
    static JPanel pannelloCalendario;
    static int veroAnno, veroMese, verogiorno, annocorrente, mesecorrente;

    static String[] mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio",
            "Agosto", "Settembre", "Ottobre",
            "Novembre", "Dicembre"};

    static String[] mesi2 = {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio",
            "agosto", "settembre", "ottobre",
            "novembre", "dicembre"};

    public static String evento;
    public static String fisso;
    public static String event;

    public static String orario;


    public JLayeredPane calendario() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ignored) {
        }

        JLayeredPane  pane = new JLayeredPane();
        pane.setSize(2000, 400);

        etichettaMese = new JLabel("");
        ComboboxAnno = new JComboBox<>();
        bottonePreview = new JButton("<-");
        bottoneProssimo = new JButton("->");
        ModelloTabellaCalendario = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return true;
            }
        };
        tabellacalendario = new JTable(ModelloTabellaCalendario);
        scroll = new JScrollPane(tabellacalendario);
        pannelloCalendario = new JPanel(null);
        pannelloCalendario.setBorder(BorderFactory.createTitledBorder("Calendario"));
        bottonePreview.addActionListener(new btnPrev_Action());
        bottoneProssimo.addActionListener(new btnNext_Action());
        ComboboxAnno.addActionListener(new cmbYear_Action());

        pane.add(pannelloCalendario);
        pannelloCalendario.add(etichettaMese);
        pannelloCalendario.add(ComboboxAnno);
        pannelloCalendario.add(bottonePreview);
        pannelloCalendario.add(bottoneProssimo);
        pannelloCalendario.add(scroll);
        pannelloCalendario.setBounds(5, 0, 1990, 400);
        etichettaMese.setBounds(100 - etichettaMese.getPreferredSize().width / 2, 25, 500, 500);
        ComboboxAnno.setBounds(1420, 350, 100, 40);
        bottonePreview.setBounds(10, 25, 100, 50);
        bottoneProssimo.setBounds(1420, 25, 100, 50);
        scroll.setBounds(0, 90, 1550, 1000);

        GregorianCalendar cal = new GregorianCalendar();
        verogiorno = cal.get(GregorianCalendar.DAY_OF_MONTH);
        veroMese = cal.get(GregorianCalendar.MONTH);
        veroAnno = cal.get(GregorianCalendar.YEAR);
        mesecorrente = veroMese;
        annocorrente = veroAnno;

        //testa dei giornii
        String[] headers = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        for (int i = 0; i < 7; i++) {
            ModelloTabellaCalendario.addColumn(headers[i]);

        }

        tabellacalendario.getColumn("Lunedì").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Martedì").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Mercoledì").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Giovedì").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Venerdì").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Sabato").setCellEditor(new TableMy(new JCheckBox()));
        tabellacalendario.getColumn("Domenica").setCellEditor(new TableMy(new JCheckBox()));

        tabellacalendario.getParent().setBackground(tabellacalendario.getBackground()); //sfondo

        tabellacalendario.getTableHeader().setResizingAllowed(false);
        tabellacalendario.getTableHeader().setReorderingAllowed(false);

        //seleziona la sngola cella
        tabellacalendario.setColumnSelectionAllowed(true);
        tabellacalendario.setRowSelectionAllowed(true);
        tabellacalendario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //set colonna e riga
        tabellacalendario.setRowHeight(38);
        ModelloTabellaCalendario.setColumnCount(7);
        ModelloTabellaCalendario.setRowCount(6);

        //inserisco anni nella combobox dal 1921 al 2121
        for (int i = veroAnno - 100; i <= veroAnno + 100; i++) {
            ComboboxAnno.addItem(String.valueOf(i));
        }

        tabellacalendario.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tabellacalendario.getSelectedRow();
                    //int row = e.getFirstRow();
                    //System.out.println(row);
                    //System.out.println("-----------------");
                    //System.out.println(currentMonth + 1);
                    //System.out.println("-----------------");
                    int column = tabellacalendario.getSelectedColumn();
                    //System.out.println(column);
                    //System.out.println("-----------------");
                    //System.out.println(tblCalendar.getValueAt(row, column));
                    Integer giorno_corrente= (int) tabellacalendario.getValueAt(row, column);
                    //System.out.println("-----------------");
                    //System.out.println(currentYear);
                    //System.out.println("-----------------");
                    Integer i = (int) tabellacalendario.getValueAt(row, column);
                    //i= i-1;
                   // Integer mese=new Integer(i);
                    Integer mese= mesecorrente +1;
                    Integer anno= annocorrente;
                    //System.out.println("-----------------");
                    //System.out.println(i);
                    //System.out.println("-----------------");
                    try {
                        evento = "";
                        evento = prendi_evento(i, mesecorrente + 1, annocorrente);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    if (evento.compareTo("Nessun evento in programma") == 0) { //0 uguale
                        fisso = "Nessun evento in programma il"+" "+giorno_corrente.toString()+"/"+mese.toString()+"/"+anno.toString();
                        try {
                            Eventi.Disegno_evento(fisso, null);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    } else {
                        fisso = "Evento per la data"+" "+giorno_corrente.toString()+"/"+mese.toString()+"/"+anno.toString()+"  alle ore:  "+orario;
                        event = evento;
                        try {
                            Eventi.Disegno_evento(fisso, event);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });
        Calendario.refreshCalendar(veroMese, veroAnno); //Refresh calendario

        try {
            Registrazione_database.testConnection();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
        return pane;
    }

    public static String prendi_evento(int giorno, int mese, int anno) throws SQLException {

        String ret;
        boolean tmp = false;
        int cont = 0;
        stringa = new String[20];

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM Eventi LIMIT 100");

        while (queryPersonale.next()) {

            if (mesi[mese - 1].equals(queryPersonale.getString("mese")) || mesi2[mese - 1].equals(queryPersonale.getString("mese"))) {
                if (anno == queryPersonale.getInt("anno")) {
                    if (giorno == queryPersonale.getInt("giorno")) {

                        orario = queryPersonale.getString("ora");
                        ret = queryPersonale.getString("evento");
                        stringa[cont] = ret;
                        cont++;
                        tmp = true;

                    }
                }
            }
        }
        if (tmp) {
            ret = "";
            for (int i = 0; i < cont; i++) {
                ret = ret + "\n" + stringa[i];
            }
            return ret;
        }
        return "Nessun evento in programma";
    }


    public static void refreshCalendar(int mese, int anno) {

        int numero_giorni, mesi_start; //NUMERO GIORNI E PARTENZA DEI MESI

        bottonePreview.setEnabled(true);
        bottoneProssimo.setEnabled(true);
        if (mese == 0 && anno <= veroAnno - 10) {
            bottonePreview.setEnabled(false);
        } //troppo presto
        if (mese == 11 && anno >= veroAnno + 100) {
            bottoneProssimo.setEnabled(false);
        } //troppo tardi
        etichettaMese.setText(mesi[mese]); //Refresh the month label (at the top)
        etichettaMese.setBounds(750 - etichettaMese.getPreferredSize().width / 2, 35, 180, 25); //Re-align label with calendar
        ComboboxAnno.setSelectedItem(String.valueOf(anno)); //Select the correct year in the combo box

        //puliamo la tabella
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                ModelloTabellaCalendario.setValueAt(null, i, j);
            }
        }

        GregorianCalendar cal = new GregorianCalendar(anno, mese, 1);
        numero_giorni = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        mesi_start = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //disegna calendario
        for (int i = 1; i <= numero_giorni; i++) {
            int row = (i + mesi_start - 2) / 7;
            int column = (i + mesi_start - 2) % 7;
            ModelloTabellaCalendario.setValueAt(i, row, column);
        }

        tabellacalendario.setDefaultRenderer(tabellacalendario.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6) { //Week-end
                setBackground(new Color(255, 220, 220));
            } else { //settimana
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == verogiorno && mesecorrente == veroMese && annocorrente == veroAnno) { //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }

    static class btnPrev_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mesecorrente == 0) { //indietro di un anno
                mesecorrente = 11;
                annocorrente -= 1;
            } else { //di un mese
                mesecorrente -= 1;
            }
            refreshCalendar(mesecorrente, annocorrente);
        }
    }

    static class btnNext_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mesecorrente == 11) { //prossimo anno
                mesecorrente = 0;
                annocorrente += 1;
            } else { //prissimo mese
                mesecorrente += 1;
            }
            refreshCalendar(mesecorrente, annocorrente);
        }
    }

    static class cmbYear_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (ComboboxAnno.getSelectedItem() != null) {
                String b = ComboboxAnno.getSelectedItem().toString();
                annocorrente = Integer.parseInt(b);
                refreshCalendar(mesecorrente, annocorrente);
            }
        }
    }

    public static void main(String[] args) {
        new Calendario();
    }
}
