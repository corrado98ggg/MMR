package it.mmr.prove;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

public class prove extends JFrame implements TableModelListener {


   public JTable table;
    public prove() {
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
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
        table = new JTable(data, columnNames);
        table.getModel().addTableModelListener(this);
        table.setSize(300,300);

        JPanel c=new JPanel();
        c.setSize(300,300);
        c.add(table);
        setSize(300,300);
        setContentPane(c);
        setVisible(true);
    }
    @Override
    public void tableChanged(TableModelEvent e) {

        int row = e.getFirstRow();
        System.out.println(row);
        int column = e.getColumn();
        System.out.println(column);
        TableModel model = (TableModel)e.getSource();
        System.out.println(model);
        String columnName = model.getColumnName(column);
        System.out.println(columnName);
        Object data = model.getValueAt(row, column);
        System.out.println(data);

    }

    public static void main(String[] args) {
        new prove();
    }
}
