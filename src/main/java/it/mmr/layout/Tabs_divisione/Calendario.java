package it.mmr.layout.Tabs_divisione;

import it.mmr.database.DBManager;
import it.mmr.database.Nuovo_evento;
import it.mmr.database.Registrazione_database;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Calendario extends JFrame {
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    public static String[] str_my;
    static JTable tblCalendar;
    static JComboBox cmbYear;
    static JPanel frmMain;

    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    static String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio",
            "Agosto", "Settembre", "Ottobre",
            "Novembre", "Dicembre"};
    static String[] months2 = {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio",
            "agosto", "settembre", "ottobre",
            "novembre", "dicembre"};

    public static String evento;
    public static String fisso;
    public static String event;


    public JLayeredPane Calendario() {
        //Look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }
        //setSize(330, 375);
        //Prepare frame
        //Set size to 400x400 pixels
      JLayeredPane  pane = new JLayeredPane();//Get content pane
        pane.setSize(2000, 400);
        //   pane.setLayout(null); //Apply null layout
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

        //Create controls
        lblMonth = new JLabel("January");
        // lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton("<-");
        btnNext = new JButton("->");
        mtblCalendar = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return true;
            }
        };
        //mtblCalendar.addTableModelListener(this);
        tblCalendar = new JTable(mtblCalendar);


        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);

        //Set border
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendario"));

        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());


        //Add controls to pane
        pane.add(pnlCalendar);
        pnlCalendar.add(lblMonth);
        //pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar);

        //Set bounds
        pnlCalendar.setBounds(5, 0, 1990, 400);
        //  pnlCalendar.setBackground(Color.white);
        lblMonth.setBounds(100 - lblMonth.getPreferredSize().width / 2, 25, 500, 500);
        //lblYear.setBounds(10, 305, 80, 20);
        cmbYear.setBounds(1420, 350, 100, 40);
        btnPrev.setBounds(10, 25, 100, 50);
        btnNext.setBounds(1420, 25, 100, 50);
        stblCalendar.setBounds(0, 90, 1550, 1000);

        //Make frame visible
        // frmMain.setResizable(false);
        //  frmMain.setVisible(true);

        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;

        //Add headers
        String[] headers = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"}; //All headers
        for (int i = 0; i < 7; i++) {
            mtblCalendar.addColumn(headers[i]);

        }

        tblCalendar.getColumn("Lunedì").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Martedì").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Mercoledì").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Giovedì").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Venerdì").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Sabato").setCellEditor(new TableMy(new JCheckBox()));
        tblCalendar.getColumn("Domenica").setCellEditor(new TableMy(new JCheckBox()));

        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);

        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Set row/column count
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);

        //Populate table
        for (int i = realYear - 100; i <= realYear + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }

        //Refresh calendar
        // setVisible(true);
        //  setContentPane(pane);+
        tblCalendar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tblCalendar.getSelectedRow();
                    //int row = e.getFirstRow();
                    System.out.println(row);
                    System.out.println("-----------------");
                    System.out.println(currentMonth + 1);
                    System.out.println("-----------------");
                    int column = tblCalendar.getSelectedColumn();
                    System.out.println(column);
                    System.out.println("-----------------");
                    System.out.println(tblCalendar.getValueAt(row, column));
                    System.out.println("-----------------");
                    System.out.println(currentYear);
                    System.out.println("-----------------");
                    int i = (int) tblCalendar.getValueAt(row, column);
                    System.out.println("-----------------");
                    System.out.println(i);
                    System.out.println("-----------------");
                    try {
                        evento = new String();
                        evento = prendi_evento(i, currentMonth + 1, currentYear);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    if (evento.compareTo("Nessun evento in programma") == 0) { //0 uguale
                        fisso = new String("Nessun evento in programma");
                        try {
                            Eventi.Disegno_evento(fisso, null);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    } else {
                        fisso = new String("Evento per la data selezionata:");
                        event = evento;
                        try {
                            Eventi.Disegno_evento(fisso, event);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                    //TableModel model = (TableModel)e.getSource();
                    //System.out.println(model);
                    //String columnName = model.getColumnName(column);
                    //System.out.println(columnName);
                    //Object data = model.getValueAt(row, column);

                    // viewerPane.add((String) table.getValueAt(row, 0), new JPanel().add(new JTextArea()));
                    //viewerPane.setSelectedIndex(viewerPane.getComponentCount()-1);


                    // etichetta_fissa = new String(Calendario.fisso);
                    // etichetta_evento = new String(Calendario.event);


                }
            }
        });
        Calendario.refreshCalendar(realMonth, realYear); //Refresh calendar

        try {
            Registrazione_database.testConnection();
            //load();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Database Error!");
        }

        return pane;

    }


    //DA FINIRE
    public static String prendi_evento(int giorno, int mese, int anno) throws SQLException {

        String ret = new String();
        //ArrayList<String> stringa_di_vettori=new ArrayList<String>();

        boolean tmp = false;
        int cont = 0;
        str_my = new String[20];

        Statement statement_tmp = DBManager.getConnection().createStatement();
        ResultSet queryPersonale = statement_tmp.executeQuery("SELECT * FROM Eventi LIMIT 100");

        while (queryPersonale.next()) {

            if (months[mese - 1].equals(queryPersonale.getString("mese")) || months2[mese - 1].equals(queryPersonale.getString("mese"))) {
                if (anno == queryPersonale.getInt("anno")) {
                    if (giorno == queryPersonale.getInt("giorno")) {

                        ret = queryPersonale.getString("evento");
                        str_my[cont] = ret;
                        cont++;
                        tmp = true;

                    }
                }
            }
        }
        if (tmp == true) {
            ret = "";
            for (int i = 0; i < cont; i++) {
                ret = ret + "\n" + str_my[i];
            }
            return ret;
        }
        return "Nessun evento in programma";
    }


    public static void refreshCalendar(int month, int year) {
        //Variables

        int nod, som; //Number Of Days, Start Of Month

        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear - 10) {
            btnPrev.setEnabled(false);
        } //Too early
        if (month == 11 && year >= realYear + 100) {
            btnNext.setEnabled(false);
        } //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(750 - lblMonth.getPreferredSize().width / 2, 35, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box

        //Clear table
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                mtblCalendar.setValueAt(null, i, j);
            }
        }

        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //Draw calendar
        for (int i = 1; i <= nod; i++) {
            int row = new Integer((i + som - 2) / 7);
            int column = (i + som - 2) % 7;
            mtblCalendar.setValueAt(i, row, column);
        }

        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6) { //Week-end
                setBackground(new Color(255, 220, 220));
            } else { //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) { //Today
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
            if (currentMonth == 0) { //Back one year
                currentMonth = 11;
                currentYear -= 1;
            } else { //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class btnNext_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentMonth == 11) { //Foward one year
                currentMonth = 0;
                currentYear += 1;
            } else { //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class cmbYear_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (cmbYear.getSelectedItem() != null) {
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }

    public static void main(String[] args) {
        new Calendario();
    }
}
