package it.mmr.layout.Divisioni;

import it.mmr.layout.Tabs_divisione.PannelloTotale;

import javax.swing.*;
import java.sql.SQLException;

public class Aereodinamica {
    public static JTabbedPane aereodinamica() throws SQLException {
        return PannelloTotale.PannelloTotale();
    }
}
