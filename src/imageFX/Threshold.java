package imageFX;


import java.awt.*;

public class Threshold {

    /**
     * Note:
     * Thresholding of an image will produce a binary image.
     * A binary image is an image having only two types of pixel - BLACK and WHITE.
     * ARGB value of BLACK = (255,0,0,0)
     * ARGB value of WHITE = (255,255,255,255)
     * BLACK pixel denotes the background color.
     * WHITE pixel denotes the foreground color.
     *
     * If the pixel intensity value is greater than threshold value then that pixel is set to WHITE.
     * else the pixel is set to BLACK.
     */


    ////////////////////////////// THRESHOLDING ////////////////////////////////

    /**
     * This method will threshold the image. It will generate a binary image.
     *
     * For a given pixel at coordinate (x,y) average pixel value is
     * avgPixelValue = (r+g+b)/3
     * If avgPixelValue > thresholdValue then set Pixel (x,y) to white
     * else set Pixel (x,y) to black
     *
     * @param img the Image object passed on which thresholding is performed.
     * @param thresholdValue value to be compared with the average pixel value.
     */
    public static void averageOfRGBValue(MyImage img, int thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int r = img.getRed(x,y);
                int g = img.getGreen(x,y);
                int b = img.getBlue(x,y);
                int avgPixelValue = (r+g+b)/3;
                if(avgPixelValue >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the image. It will generate a binary image.
     *
     * @param img the Image object passed on which thresholding is performed.
     * @param thresholdValue value to be compared with the average pixel value.
     */
    public static void toBinary(MyImage img, int thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int r = img.getRed(x,y);
                int g = img.getGreen(x,y);
                int b = img.getBlue(x,y);
                int tmp = (int)(0.2126*r + 0.7152*g + 0.0722*b);
                if(tmp >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the RED image using RED pixel only. It will generate a binary image.
     *
     * For a given pixel at coordinate (x,y)
     * If R > thresholdValue then set Pixel (x,y) to white
     * else set Pixel (x,y) to black
     *
     * @param img The red image to threshold.
     * @param thresholdValue value to be compared with the R pixel value.
     */
    public static void redPixel(MyImage img, int thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int r = img.getRed(x,y);
                if(r >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the GREEN image using GREEN pixel only. It will generate a binary image.
     *
     * For a given pixel at coordinate (x,y)
     * If G > thresholdValue then set Pixel (x,y) to white
     * else set Pixel (x,y) to black
     *
     * @param img The green image to threshold.
     * @param thresholdValue value to be compared with the G pixel value.
     */
    public static void greenPixel(MyImage img, int thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int g = img.getGreen(x,y);
                if(g >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the BLUE image using BLUE pixel only. It will generate a binary image.
     *
     * For a given pixel at coordinate (x,y)
     * If B > thresholdValue then set Pixel (x,y) to white
     * else set Pixel (x,y) to black
     *
     * @param img The blue image to threshold.
     * @param thresholdValue value to be compared with the R pixel value.
     */
    public static void bluePixel(MyImage img, int thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int b = img.getBlue(x,y);
                if(b >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    public static void saturationHSB(MyImage img, double thresholdValue){
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                float[] hsbvals = new float[3];
                hsbvals = Color.RGBtoHSB(img.getRed(x,y), img.getBlue(x,y), img.getGreen(x,y), hsbvals);
                float s = hsbvals[1];
                if(s >= thresholdValue){
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }else{
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }
            }
        }
    }

    //////////////////////////// AUTO THRESHOLDING /////////////////////////////

    /**
     * This method will threshold the image. It will generate a binary image.
     * This method will calculate the thresholdValue and
     * If avgOfRGB > thresholdValue then set Pixel (x,y) to WHITE
     * else set Pixel (x,y) to BLACK
     *
     * @param img the Image object passed on which thresholding is performed.
     */
    public static void autoThreshold(MyImage img){

        /**
         * thresholdValue will hold the final threshold value for the image.
         * Initially thresholdValue = 127 [i.e. 255/2 = 127 (integer part)]
         *
         * iThreshold will hold the intermediate threshold value during computation
         * of final threshold value.
         */
        int thresholdValue = 127, iThreshold;

        /**
         * sum1 holds the sum of avgOfRGB values less than thresholdValue.
         * sum2 holds the sum of avgOfRGB values greater than or equal to the thresholdValue.
         * count1 holds the number of pixels whose avgOfRGB value is less than thresholdValue.
         * count2 holds the number of pixels whose avgOfRGB value is greater than or equal to thresholdValue.
         */
        int sum1, sum2, count1, count2;

        /**
         * mean1 is equal to sum1/count1.
         * mean2 is equal to sum2/count2.
         */
        int mean1, mean2;

        /** calculating thresholdValue */
        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getImageHeight(); y++){
                for(int x = 0; x < img.getImageWidth(); x++){
                    int r = img.getRed(x,y);
                    int g = img.getGreen(x, y);
                    int b = img.getGreen(x, y);
                    int avgOfRGB = (r+g+b)/3;

                    if(avgOfRGB < thresholdValue){
                        sum1 += avgOfRGB;
                        count1++;
                    }else{
                        sum2 += avgOfRGB;
                        count2++;
                    }
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(int)(sum1/count1):0;
            mean2 = (count2 > 0)?(int)(sum2/count2):0;

            /** calculating intermediate threshold */
            iThreshold = (mean1 + mean2)/2;

            if(thresholdValue != iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
        }

        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int r = img.getRed(x,y);
                int g = img.getGreen(x, y);
                int b = img.getBlue(x, y);
                int avgOfRGB = (r+g+b)/3;

                if(avgOfRGB >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the image. It will generate a binary image.
     * This method will calculate the thresholdValue and
     * If redValueOfPixel > thresholdValue then set Pixel (x,y) to WHITE
     * else set Pixel (x,y) to BLACK
     *
     * @param img the Image object passed on which thresholding is performed.
     */
    public static void autoThreshold_usingRedValueOfPixels(MyImage img){

        /**
         * thresholdValue will hold the final threshold value for the image.
         * Initially thresholdValue = 127 [i.e. 255/2 = 127 (integer part)]
         *
         * iThreshold will hold the intermediate threshold value during computation
         * of final threshold value.
         */
        int thresholdValue = 127, iThreshold;

        /**
         * sum1 holds the sum of red values less than thresholdValue.
         * sum2 holds the sum of red values greater than or equal to the thresholdValue.
         * count1 holds the number of pixels whose red value is less than thresholdValue.
         * count2 holds the number of pixels whose red value is greater than or equal to thresholdValue.
         */
        int sum1, sum2, count1, count2;

        /**
         * mean1 is equal to sum1/count1.
         * mean2 is equal to sum2/count2.
         */
        int mean1, mean2;

        /** calculate thresholdValue using only the R value of the pixel */
        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getImageHeight(); y++){
                for(int x = 0; x < img.getImageWidth(); x++){
                    int r = img.getRed(x,y);
                    if(r < thresholdValue){
                        sum1 += r;
                        count1++;
                    }else{
                        sum2 += r;
                        count2++;
                    }
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(int)(sum1/count1):0;
            mean2 = (count2 > 0)?(int)(sum2/count2):0;

            /** calculating intermediate threshold */
            iThreshold = (mean1 + mean2)/2;

            if(thresholdValue != iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
        }

        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int r = img.getRed(x,y);
                if(r >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the image. It will generate a binary image.
     * This method will calculate the thresholdValue and
     * If saturation > thresholdValue then set Pixel (x,y) to WHITE
     * else set Pixel (x,y) to BLACK
     *
     * @param img the Image object passed on which thresholding is performed.
     */
    public static void autoThreshold_usingSaturationOfPixelsInHSB(MyImage img){
        double thresholdValue = 0.5, iThreshold;

        double sum1, sum2;
        int count1, count2;
        double mean1, mean2;

        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getImageHeight(); y++){
                for(int x = 0; x < img.getImageWidth(); x++){
                    float[] hsbvals = new float[3];
                    hsbvals = Color.RGBtoHSB(img.getRed(x,y), img.getBlue(x,y), img.getGreen(x,y), hsbvals);
                    float s = hsbvals[1];
                    if (s < 0.003){
                        continue;
                    }
                    if(s < thresholdValue){
                        sum1 += s;
                        count1++;
                    }else{
                        sum2 += s;
                        count2++;
                    }
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(sum1/count1):0;
            mean2 = (count2 > 0)?(sum2/count2):0;

            /** calculating intermediate threshold */
            iThreshold = (mean1 + mean2)/2;

            if(thresholdValue != iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
        }

        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                float[] hsbvals = new float[3];
                hsbvals = Color.RGBtoHSB(img.getRed(x,y), img.getBlue(x,y), img.getGreen(x,y), hsbvals);
                float s = hsbvals[1];
                if(s >= thresholdValue){
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }else{
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }
            }
        }

    }

    /**
     * This method will threshold the image. It will generate a binary image.
     * This method will calculate the thresholdValue and
     * If greenValueOfPixel > thresholdValue then set Pixel (x,y) to WHITE
     * else set Pixel (x,y) to BLACK
     *
     * @param img the Image object passed on which thresholding is performed.
     */
    public static void autoThreshold_usingGreenValueOfPixels(MyImage img){

        /**
         * thresholdValue will hold the final threshold value for the image.
         * Initially thresholdValue = 127 [i.e. 255/2 = 127 (integer part)]
         *
         * iThreshold will hold the intermediate threshold value during computation
         * of final threshold value.
         */
        int thresholdValue = 127, iThreshold;

        /**
         * sum1 holds the sum of green values less than thresholdValue.
         * sum2 holds the sum of green values greater than or equal to the thresholdValue.
         * count1 holds the number of pixels whose green value is less than thresholdValue.
         * count2 holds the number of pixels whose green value is greater than or equal to thresholdValue.
         */
        int sum1, sum2, count1, count2;

        /**
         * mean1 is equal to sum1/count1.
         * mean2 is equal to sum2/count2.
         */
        int mean1, mean2;

        /** calculate thresholdValue using only the G value of the pixel */
        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getImageHeight(); y++){
                for(int x = 0; x < img.getImageWidth(); x++){
                    int g = img.getGreen(x,y);
                    if(g < thresholdValue){
                        sum1 += g;
                        count1++;
                    }else{
                        sum2 += g;
                        count2++;
                    }
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(int)(sum1/count1):0;
            mean2 = (count2 > 0)?(int)(sum2/count2):0;

            /** calculating intermediate threshold */
            iThreshold = (mean1 + mean2)/2;

            if(thresholdValue != iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
        }

        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int g = img.getGreen(x,y);
                if(g >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

    /**
     * This method will threshold the image. It will generate a binary image.
     * This method will calculate the thresholdValue and
     * If blueValueOfPixel > thresholdValue then set Pixel (x,y) to WHITE
     * else set Pixel (x,y) to BLACK
     *
     * @param img the Image object passed on which thresholding is performed.
     */
    public static void autoThreshold_usingBlueValueOfPixels(MyImage img){

        /**
         * thresholdValue will hold the final threshold value for the image.
         * Initially thresholdValue = 127 [i.e. 255/2 = 127 (integer part)]
         *
         * iThreshold will hold the intermediate threshold value during computation
         * of final threshold value.
         */
        int thresholdValue = 127, iThreshold;

        /**
         * sum1 holds the sum of blue values less than thresholdValue.
         * sum2 holds the sum of blue values greater than or equal to the thresholdValue.
         * count1 holds the number of pixels whose blue value is less than thresholdValue.
         * count2 holds the number of pixels whose blue value is greater than or equal to thresholdValue.
         */
        int sum1, sum2, count1, count2;

        /**
         * mean1 is equal to sum1/count1.
         * mean2 is equal to sum2/count2.
         */
        int mean1, mean2;

        /** calculating thresholdValue using only the B value of the pixel */
        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getImageHeight(); y++){
                for(int x = 0; x < img.getImageWidth(); x++){
                    int b = img.getBlue(x,y);
                    if(b < thresholdValue){
                        sum1 += b;
                        count1++;
                    }else{
                        sum2 += b;
                        count2++;
                    }
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(int)(sum1/count1):0;
            mean2 = (count2 > 0)?(int)(sum2/count2):0;

            /** calculating intermediate threshold */
            iThreshold = (mean1 + mean2)/2;

            if(thresholdValue != iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
        }

        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                int b = img.getBlue(x,y);
                if(b >= thresholdValue){
                    img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                    img.setPixel(x,y,255,0,0,0);  //set BLACK
                }
            }
        }
    }

}