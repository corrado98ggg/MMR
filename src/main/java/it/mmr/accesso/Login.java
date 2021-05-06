/*package it.mmr.accesso;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

/**
 * creazione interfaccia di login:
 *
 */

/*
public class Login extends JFrame implements ActionListener {

    /*@Serial
    private static final long serialVersionUID = 1L;
    private final JButton exit;
    public JButton ok;
    int selectedItem;*/


  /*  public Login(){

        super("Login");


        BufferedImage logo_uni = null;
        try {
            logo_uni = ImageIO.read(new File("src/main/java/images/logo_uni.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage logo_mmr = null;
        try {
            logo_mmr = ImageIO.read(new File("src/main/java/images/mmr_logo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert logo_mmr != null;
        assert logo_uni != null;

        //BufferedImage resized_logo_mmr = getScaledDimension(logo_mmr, 300, 400);
        //BufferedImage resized_logo_uni = getScaledDimension(logo_uni, 600, 800);

        ok = new JButton("OK");
        ok.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        JTextField utente = new JTextField("Inserisci nome utente");
        JPasswordField password = new JPasswordField("Inserisci password");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));

        JLabel picLabel = new JLabel(new ImageIcon(resized_logo_mmr));
        JLabel piclabel2 = new JLabel(new ImageIcon(resized_logo_uni));


        panel.add(piclabel2);
        panel.add(picLabel);

        panel.add(utente);
        panel.add(password);
        panel.add(ok);
        panel.add(exit);

        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 300);
        setVisible(true);
    }


   /* public BufferedImage getScaledDimension(BufferedImage img, int MAX_IMG_WIDTH, int MAX_IMG_HEIGHT) {

        img.getWidth();
        int width;
        var height = img.getHeight();

        Dimension originalDimension = new Dimension(img.getWidth(),
                img.getHeight());
        Dimension boundaryDimension = new Dimension(MAX_IMG_WIDTH,
                MAX_IMG_HEIGHT);
        Dimension scalingDimension = getScaledDimension(originalDimension,
                boundaryDimension);

        width = (int) scalingDimension.getWidth();
        height = (int) scalingDimension.getHeight();

        BufferedImage resizedImage = new BufferedImage(width, height,
                img.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(img, 0, 0, width, height, null);
        return resizedImage;
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // Controllo se e' necessario eseguire lo scaling
        if (original_width > bound_width) {
            //scaling della larghezza in base alla larghezza massima
            new_width = bound_width;
            //eseguo lo scaling dell'altezza per mantenere le proporzioni
            new_height = (new_width * original_height) / original_width;
        }

        // Dopo aver calcolato la nuova altezza, controllo se è ancora fuori limite
        if (new_height > bound_height) {

            new_height = bound_height;
            //rieseguo lo scaling per mantenere le proporzioni
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
*/

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
  /*  @Override
    public void actionPerformed(ActionEvent e) {

        //se il tasto premuto è: fai->
        if(e.getSource() == exit){
            //chiudo l'applicazione:
        }

        if(e.getSource() == ok){

        }
    }
}
*/
