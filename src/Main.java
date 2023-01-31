import imageFX.*;
import imageFX.morph.Closing;
import imageFX.morph.Dilation;
import imageFX.morph.Erosion;
import imageFX.morph.Opening;

public class Main {
    public static void main(String[] args) {
        MyImage img = new MyImage();
        img.readImage("/Users/stasmoska/IdeaProjects/PJAVA2/Temat8-kopia/ALL_IDB1/im/Im007_1.jpg");
        MyImage img2 = new MyImage(img);
        Threshold.autoThreshold_usingSaturationOfPixelsInHSB(img2);
//        Opening.binaryImage(img2,true,true);
        Dilation.binaryImage(img2, false);
        //Dilation.binaryImage(img2, false);
        Opening.binaryImage(img2,false,false);
        //Opening.binaryImage(img2,false,false);
        //Erosion.binaryImage(img2,true);


        toGrey.RGBtoGREY(img);
        img2.binaryNegative();
        img.combinedWithImage(img2,false);
        Threshold.autoThreshold(img);
        Erosion.binaryImage(img,true);

        Closing.binaryImage(img);

        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);


        Closing.binaryImage(img);


//        Closing.binaryImage(img);
//        Closing.binaryImage(img);
//        Closing.binaryImage(img);
//        Closing.binaryImage(img);

       /* // Do wyboru ktory
        Threshold.autoThreshold_usingSaturationOfPixelsInHSB(img);
        //Threshold.autoThreshold_usingRedValueOfPixels(img);
        //Threshold.saturationHSB(img,0.17);

        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);
        Dilation.binaryImage(img,false);


        Erosion.binaryImage(img,true);

        //Closing.binaryImage(img);
        img.binaryNegative();
        img2.combinedWithImage(img,false);
        Threshold.autoThreshold_usingSaturationOfPixelsInHSB(img2);
        Closing.binaryImage(img2);
        Closing.binaryImage(img2);
        Closing.binaryImage(img2);
        Closing.binaryImage(img2);
        Closing.binaryImage(img2);
        Closing.binaryImage(img2);*/



        img.writeImage("/Users/stasmoska/IdeaProjects/PJAVA2/nowe/tresh_mask.jpg");
        img2.writeImage("/Users/stasmoska/IdeaProjects/PJAVA2/nowe/tresh_final.jpg");
    }
}