package it.mmr.Funzioni_comuni;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;



public class CalendarFrame extends JFrame  implements Runnable{


JPanel calendario=new JPanel();
    @Override
    public void run() {

        // Mese corrente
        Calendar today = Calendar.getInstance();
        int tMonth = today.get(Calendar.MONTH);
        int tYear = today.get(Calendar.YEAR);

        // MonthPanel panel = new MonthPanel(tMonth , 2021);

        setTitle("Calendario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });

        JTabbedPane scorrimento = new JTabbedPane();

        //scorrimento.addTab("Gennaio", new JScrollPane(panel));
        String mesi[] = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };
        int mesi_number[] = {0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayList<JPanel> a = new ArrayList<JPanel>(12);
        MonthPanel tmp;

        for(int i = 0; i < 11; i++){
            a.add(i,new JPanel());
            tmp = new MonthPanel(mesi_number[i], tYear);
            a.get(i).add(tmp);
            scorrimento.addTab(String.valueOf(mesi[i]), a.get(i));
        }

        scorrimento.setSelectedIndex(tMonth);

        setLayout(new FlowLayout());
        scorrimento.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(scorrimento);
       /* pack();
        setBounds(0,0, 1000, 800);
        setVisible(true);*/
    }

    public void exitProcedure() {
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(new CalendarFrame());
        //new CalendarFrame();

    }

}