package it.mmr.Icon;

import it.mmr.database.Registrazione_database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Creazione_immagini {

    public static BufferedImage Creazione_immagini(String path, int width, int height){

        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage resized_icon = Registrazione_database.getScaledDimension(icon, width, height);
        return resized_icon;
    }
}
