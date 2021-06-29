package it.mmr.Icon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Creazione_immagini {

    public static BufferedImage creazioneImmagini(String path, int width, int height){

        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert icon != null;
        return dimensionescalata(icon, width, height);
    }

    public static BufferedImage dimensionescalata(BufferedImage img, int MAX_IMG_WIDTH, int MAX_IMG_HEIGHT) {

        img.getWidth();
        int lunghezza;
        var altezza = img.getHeight();

        Dimension dimensioneOriginale = new Dimension(img.getWidth(),
                img.getHeight());

        Dimension massimaDimensione = new Dimension(MAX_IMG_WIDTH,
                MAX_IMG_HEIGHT);

        Dimension dimensioneScalataa = dimensionescalata(dimensioneOriginale,
                massimaDimensione);

        lunghezza = (int) dimensioneScalataa.getWidth();
        altezza = (int) dimensioneScalataa.getHeight();

        BufferedImage resizedImage = new BufferedImage(lunghezza, altezza,
                img.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(img, 0, 0, lunghezza, altezza, null);
        return resizedImage;
    }

    public static Dimension dimensionescalata(Dimension dimensioneOriginal, Dimension Max) {

        int original_lunghezza = dimensioneOriginal.width;
        int original_altezza = dimensioneOriginal.height;
        int bound_lunghezza = Max.width;
        int bound_altezza = Max.height;
        int new_lunghezza = original_lunghezza;
        int new_altezza = original_altezza;

        // Controllo se e' necessario eseguire lo scaling
        if (original_lunghezza > bound_lunghezza) {
            //eseguo lo scaling della larghezza in base alla larghezza voluta
            new_lunghezza = bound_lunghezza;
            //eseguo lo scaling dell'altezza per mantenere le proporzioni e calcolo l'altezza
            new_altezza = (new_lunghezza * original_altezza) / original_lunghezza;
        }

        // Dopo aver calcolato la nuova altezza, calcolo la lunghezza
        if (new_altezza > bound_altezza) {

            new_altezza = bound_altezza;
            //rieseguo lo scaling per mantenere le proporzioni
            new_lunghezza = (new_altezza * original_lunghezza) / original_altezza;
        }

        return new Dimension(new_lunghezza, new_altezza);
    }
}
