package it.mmr.Prove;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Provacal extends JFrame{

    public Provacal()
    {
        String [] columnNames = {"First Name",
                "Cognome",
                "Sport",
                "N. di anni",
                "Vegetariano"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };
        JTable table = new JTable(data, columnNames);
        //table.setEnabled(false);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    System.out.println(
                            row
                    );
                   // viewerPane.add((String) table.getValueAt(row, 0), new JPanel().add(new JTextArea()));
                    //viewerPane.setSelectedIndex(viewerPane.getComponentCount()-1);
                }
            }
        });
       // JPanel a=new JPanel();
        setContentPane(table);
        setVisible(
                true
        );
        setSize(500,500);
    }

    public static void main(String[] args) {
        new Provacal();
    }
}
