package imageFX;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class MyImage {

    //////////////////////////// VARIABLES /////////////////////////////////////

    /** Store the Image reference */
    public BufferedImage image;

    /** Store the image width and height */
    private int width, height;

    /** Pixels value - ARGB */
    private int pixels[];

    /** Total number of pixel in an image*/
    private int totalPixels;

    /**
     * Image type example: jpg|png
     * JPG does not support alpha (transparency is lost) while PNG supports alpha.
     */
    private enum ImageType{
        JPG, PNG
    };

    private ImageType imgType;

    ////////////////////////////////// CONSTRUCTORS ////////////////////////////

    /** Default constructor */
    public MyImage(){}

    /**
     * Constructor to create a new image object
     *
     * @param width width of the image passed by the user
     * @param height height of the image passed by the user
     */
    public MyImage(int width, int height){
        this.width = width;
        this.height = height;
        this.totalPixels = this.width * this.height;
        this.pixels = new int[this.totalPixels];
        image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.imgType = ImageType.PNG;
        initPixelArray();
    }

    /**
     * Constructor to create a copy of a previously created image object.
     *
     * @param img The image object whose copy is created.
     */
    public MyImage(MyImage img){
        this.width = img.getImageWidth();
        this.height = img.getImageHeight();
        this.totalPixels = this.width * this.height;
        this.pixels = new int[this.totalPixels];

        this.imgType = img.imgType;
        if(this.imgType == ImageType.PNG){
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }else{
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        //copy original image pixels value to new image and pixels array
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                this.image.setRGB(x, y, img.getPixel(x, y));
                this.pixels[x+y*this.width] = img.getPixel(x, y);
            }
        }
    }

    /////////////////////////////////////// METHODS ////////////////////////////

    /**
     * This method will modify the image object.
     *
     * @param width The width of the new image.
     * @param height The height of the new image.
     * @param bi The new image that will replace the old image.
     */
    public void modifyImageObject(int width, int height, BufferedImage bi){
        this.width = width;
        this.height = height;
        this.totalPixels = this.width * this.height;
        this.pixels = new int[this.totalPixels];
        if(this.imgType == ImageType.PNG){
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }else{
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2d = this.image.createGraphics();
        g2d.drawImage(bi, 0, 0, null);
        g2d.dispose();
        initPixelArray();
    }

    /**
     * Read the image using the image file path passed
     *
     * @param filePath the path of the image file
     * Example filePath = "D:\\LogoJava.jpg"
     */
    public void readImage(String filePath){
        try{
            File f = new File(filePath);
            image = ImageIO.read(f);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            if("jpg".equals(fileType)){
                imgType = ImageType.JPG;
            }else{
                imgType = ImageType.PNG;
            }
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.totalPixels = this.width * this.height;
            this.pixels = new int[this.totalPixels];
            initPixelArray();
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
    }

    /**
     * Write the content of the BufferedImage object 'image' to a file
     *
     * @param filePath complete file path of the output image file to be created
     * Example filePath = "D:\\Output.jpg"
     */
    public void writeImage(String filePath){
        try{
            File f = new File(filePath);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            ImageIO.write(image, fileType, f);
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
    }

    /**
     * Initialize the pixel array
     * Image origin is at coordinate (0,0)
     *
     * This method will store the value of each pixel of a 2D image in a 1D array.
     */
    private void initPixelArray(){
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try{
            pg.grabPixels();
        }catch(InterruptedException e){
            System.out.println("Error Occurred: "+e);
        }
    }

    /**
     * This method will check for equality of two images.
     * If we have two image img1 and img2
     * Then calling img1.isEqual(img2) will return TRUE if img1 and img2 are equal
     * else it will return FALSE.
     *
     * @param img The image to check with.
     * @return TRUE if two images are equal else FALSE.
     */
    public boolean isEqual(MyImage img){
        //check dimension
        if(this.width != img.getImageWidth() || this.height != img.getImageHeight()){
            return false;
        }

        //check pixel values
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                if(this.pixels[x+y*this.width] != img.getPixel(x, y)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method will invert binary value of the image
     */
    public void binaryNegative(){
        for (int y = 0; y < this.height; y++){
            for (int x = 0; x < this.width; x++){
                if (this.getRed(x,y) == 255 && this.getGreen(x,y) == 255 && this.getBlue(x,y) == 255){
                    this.setPixel(x,y,255,0,0,0);
                }
                else {
                    this.setPixel(x,y,255,255,255,255);
                }
            }
        }
    }



    /////////////////////////// GET, SET AND UPDATE METHODS ////////////////////
    /**
     * Return the Buffered image
     *
     * @return image as BufferedImage
     */
    public BufferedImage getImage(){
        return this.image;
    }

    /**
     * Return the image width
     *
     * @return the width of the image
     */
    public int getImageWidth(){
        return width;
    }

    /**
     * Return the image height
     *
     * @return the height of the image
     */
    public int getImageHeight(){
        return height;
    }

    /**
     * Return total number of pixels in the image
     *
     * @return the total number of pixels
     */
    public int getImageTotalPixels(){
        return totalPixels;
    }

    /**
     * This method will return the amount of alpha value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of alpha (transparency)
     *
     * 0 means transparent
     * 255 means opaque
     */
    public int getAlpha(int x, int y){
        return (pixels[x+(y*width)] >> 24) & 0xFF;
    }

    /**
     * This method will return the amount of red value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of red
     *
     * 0 means none
     * 255 means fully red
     */
    public int getRed(int x, int y){
        return (pixels[x+(y*width)] >> 16) & 0xFF;
    }

    /**
     * This method will return the amount of green value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of green
     *
     * 0 means none
     * 255 means fully green
     */
    public int getGreen(int x, int y){
        return (pixels[x+(y*width)] >> 8) & 0xFF;
    }

    /**
     * This method will return the amount of blue value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of blue
     *
     * 0 means none
     * 255 means fully blue
     */
    public int getBlue(int x, int y){
        return pixels[x+(y*width)] & 0xFF;
    }

    /**
     * This method will return the pixel value of the pixel at the coordinate (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the pixel value in integer [Value can be negative and positive.]
     */
    public int getPixel(int x, int y){
        return pixels[x+(y*width)];
    }


    /**
     * This method will set the amount of red value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param red the red value to set
     *
     * 0 means none
     * 255 means fully red
     */
    public void setRed(int x, int y, int red){
        pixels[x+(y*width)] = (red<<16) | (pixels[x+(y*width)] & 0xFF00FFFF);
        updateImagePixelAt(x,y);
    }

    /**
     * This method will set the amount of green value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param green the green value to set
     *
     * 0 means none
     * 255 means fully green
     */
    public void setGreen(int x, int y, int green){
        pixels[x+(y*width)] = (green<<8) | (pixels[x+(y*width)] & 0xFFFF00FF);
        updateImagePixelAt(x,y);
    }

    /**
     * This method will set the amount of blue value between 0-255 at the pixel (x,y)
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param blue the blue value to set
     *
     * 0 means none
     * 255 means fully blue
     */
    public void setBlue(int x, int y, int blue){
        pixels[x+(y*width)] = blue | (pixels[x+(y*width)] & 0xFFFFFF00);
        updateImagePixelAt(x,y);
    }

    /**
     * This method will set pixel(x,y) to ARGB value.
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param alpha the alpha value to set [value from 0-255]
     * @param red the red value to set [value from 0-255]
     * @param green the green value to set [value from 0-255]
     * @param blue the blue value to set  [value from 0-255]
     */
    public void setPixel(int x, int y, int alpha, int red, int green, int blue){
        pixels[x+(y*width)] = (alpha<<24) | (red<<16) | (green<<8) | blue;
        updateImagePixelAt(x,y);
    }

    /**
     * This method will set pixel (x,y) to pixelValue.
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param pixelValue the pixel value to set
     */
    public void setPixelToValue(int x, int y, int pixelValue){
        pixels[x+(y*width)] = pixelValue;
        updateImagePixelAt(x,y);
    }


    /**
     * This method will update the image pixel at coordinate (x,y)
     *
     * @param x the x coordinate of the pixel that is set
     * @param y the y coordinate of the pixel that is set
     */
    private void updateImagePixelAt(int x, int y){
        image.setRGB(x, y, pixels[x+(y*width)]);
    }

    /**
     * This metod will combine image with binary mask
     *
     * @param mask binary image
     * @param pixelColor If set to TRUE will perform on BLACK pixels else on WHITE pixels.
     */
    public void combinedWithImage(MyImage mask, boolean pixelColor){
        for(int y = 0; y < mask.getImageHeight(); y++){
            for(int x = 0; x < mask.getImageWidth(); x++) {
                int a = mask.getAlpha(x,y);
                int r = mask.getRed(x,y);
                int g = mask.getBlue(x,y);
                int b = mask.getGreen(x,y);
                if(pixelColor){
                    if(r == 0 && g == 0 && b == 0){
                        this.setPixel(x,y,255,255,255,255);
                    }
                }
                else{
                    if(r == 255 && g == 255 && b == 255){
                        this.setPixel(x,y,255,255,255,255);
                    }
                }


            }
        }

    }

    ////////////////////////////// HSV color model Methods /////////////////////

    /**
     * This method will return the hue of the pixel (x,y) as per HSV color model.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @return H The hue value of the pixel [0-360] in degree.
     */
    public double HSV_getHue(int x, int y){
        int r = getRed(x,y);
        int g = getGreen(x,y);
        int b = getBlue(x,y);

        double H = Math.toDegrees(Math.acos((r - (0.5*g) - (0.5*b))/Math.sqrt((r*r)+(g*g)+(b*b)-(r*g)-(g*b)-(b*r))));
        H = (b>g)?360-H:H;

        return H;
    }

    /**
     * This method will return the saturation of the pixel (x,y) as per HSV color model.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @return S The saturation of the pixel [0-1].
     */
    public double HSV_getSaturation(int x, int y){
        int r = getRed(x,y);
        int g = getGreen(x,y);
        int b = getBlue(x,y);

        int max = Math.max(Math.max(r, g), b);
        int min = Math.min(Math.min(r, g), b);

        double S = (max>0)?(1 - (double)min/max):0;

        return S;
    }

    /**
     * This method will return the value of the pixel (x,y) as per HSV color model.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @return V The value of the pixel [0-1].
     */
    public double HSV_getValue(int x, int y){
        int r = getRed(x,y);
        int g = getGreen(x,y);
        int b = getBlue(x,y);

        int max = Math.max(Math.max(r, g), b);
        double V = max/255.0;

        return V;
    }

}