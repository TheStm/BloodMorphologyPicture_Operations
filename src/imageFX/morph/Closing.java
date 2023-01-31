package imageFX.morph;
import imageFX.MyImage;


public class Closing {

    /**
     * This method will perform closing on binary image img.
     *
     * @param img The image on which opening is performed.
     */
    public static void binaryImage(MyImage img){
        Dilation.binaryImage(img, true);
        Erosion.binaryImage(img, true);
    }

    /**
     * This method will perform closing on grayscale image img.
     *
     * @param img The image on which opening is performed.
     * @param mask The square mask
     * @param maskSize The size of the square mask [No. of rows or columns of the square mask]
     */
    public static void grayscaleImage(MyImage img, int mask[], int maskSize){
        Dilation.grayscaleImage(img, mask, maskSize);
        Erosion.grayscaleImage(img, mask, maskSize);
    }
}