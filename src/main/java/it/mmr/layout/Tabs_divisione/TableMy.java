package it.mmr.layout.Tabs_divisione;

import javax.swing.*;
import java.util.EventObject;

public class TableMy extends DefaultCellEditor {

    public TableMy(JCheckBox checkBox) {
        super(checkBox);
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return false;
    }
}
