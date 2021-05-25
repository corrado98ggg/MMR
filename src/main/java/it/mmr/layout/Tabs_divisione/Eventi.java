package it.mmr.layout.Tabs_divisione;


import it.mmr.database.Registrazione_database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Eventi extends JFrame{
    
    public JPanel contenitore;
    public JLabel eventi;
    public JPanel tasto_piu;
    public JPanel contenitore_eventi;
    public JButton piu;
    
    public static String etichetta_evento;
   // public Component Eventi;

    public JPanel Eventi() throws SQLException {

        contenitore = new JPanel();
        
        
        etichetta_evento= new String("ciaone");
        
        piu = new JButton("piu");
        tasto_piu=new JPanel();
        tasto_piu.add(piu);
        contenitore.add(tasto_piu, 1, 0);
        
        
       
        eventi = new JLabel(etichetta_evento);
        contenitore_eventi=new JPanel();
        contenitore_eventi.add(eventi);
        
        contenitore.add(contenitore_eventi,0,0);
        contenitore.setBounds(0,400,2000,1000);
        
        eventi.setEnabled(false);
        

        return contenitore;
    }
    
}