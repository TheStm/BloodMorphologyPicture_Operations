package imageFX.morph;
import imageFX.MyImage;


public class Opening {

    /**
     * This method will perform opening on image img.
     *
     * For erosion we generally consider foreground pixel. So, erodeBlackpixel = false
     * For dilation we generally consider the background pixel. So, dilateBlackPixel = true.
     *
     * @param img The image on which opening is performed.
     * @param erodeBlackPixel If set to TRUE will perform erosion on BLACK pixels else on WHITE pixels.
     * @param dilateBlackPixel If set to TRUE will perform dilation on BLACK pixels else on WHITE pixels.
     */
    public static void binaryImage(MyImage img, boolean erodeBlackPixel, boolean dilateBlackPixel){
        Erosion.binaryImage(img, erodeBlackPixel);
        Dilation.binaryImage(img, dilateBlackPixel);
    }
}