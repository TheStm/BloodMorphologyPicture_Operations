package imageFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class toGrey {
    /**
     * This method converts RGB image to grey scale
     * @param img The image on which grey scale is applied on
     */
    public static void RGBtoGREY(MyImage img){

        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++) {

                int r = (int)(img.getRed(x,y) * 0.299);
                int b = (int)(img.getBlue(x,y) * 0.587);
                int g = (int)(img.getGreen(x,y) * 0.114);
                Color newColor = new Color(r+g+b,r+b+g,r+b+g);
                img.setRed(x,y,newColor.getRed());
                img.setBlue(x,y,newColor.getGreen());
                img.setGreen(x,y,newColor.getBlue());
            }
        }

    }
}
