import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import imageFX.*;
import imageFX.morph.Closing;
import imageFX.morph.Dilation;
import imageFX.morph.Erosion;
import imageFX.morph.Opening;

/**
 * create a GUI Swing Menu
 */
public class SwingMenu implements ActionListener, ItemListener, AdjustmentListener {
    String PATH = "/resources/images/start.png";
    public static class CustomButton extends JButton
    {
        /**
         * create custom buttons with preset size and color
         * @param text the text that appears on the button
         */
        public CustomButton(String text)
        {
            setText(text);
            setOpaque(true);
            setBackground(Color.white);
            setPreferredSize(new Dimension(90,20));
        }

    }
    /**
     * declare objects
     */
    private JFrame frame;
    private JPanel panel, panel_options, panel_images, panel_au_ma, panel_threshold, panel_none, panel_auto,
            panel_manual, panel_app_save_combine, panel_arrow, panel_imag_arr;
    private JLabel text_1, text_2, text_3, text_4, image_1, image_2;
    private CustomButton button_load, button_none, button_manual, button_auto, button_left, button_right, button_apply,
            button_save, button_combine;
    private BasicArrowButton left, right;
    private CheckboxGroup morph_operations;
    private Checkbox none, opening, closing, dilation, erosion, greyScale, invert;
    private Choice manual, auto;
    private Scrollbar scroll;
    private CardLayout card;
    private Object z1;
    private String msg = "Value: 0";

    MyImage img1 = new MyImage();
    MyImage img2 = new MyImage();
    MyImage img3 = new MyImage();
    MyImage previous = new MyImage();
    MyImage swapImg = new MyImage();
    String morf = "";
    String autoLvl = "none";
    String thresholdType = "";
    int saveIterator = 1;

    /**
     * the main algorithm that creates the window and its elements
     */
    public void Algorithm()
    {
        /**
         * create objects
         */
        frame = new JFrame();
        panel = new JPanel();
        panel_options = new JPanel();
        panel_images = new JPanel();
        panel_au_ma = new JPanel();
        panel_threshold = new JPanel();
        panel_none = new JPanel();
        panel_manual = new JPanel();
        panel_auto = new JPanel();
        panel_app_save_combine = new JPanel();
        panel_arrow = new JPanel();
        panel_imag_arr = new JPanel();

        text_1 = new JLabel("<html>Welcome to Morphology Image Analyzer!<html>", SwingConstants.CENTER);
        text_2 = new JLabel("Morphological operations");
        text_3 = new JLabel("Threshold");
        text_4 = new JLabel(msg);

        button_load = new CustomButton("Load");
        button_none = new CustomButton("None");
        button_manual = new CustomButton("Manual");
        button_auto = new CustomButton("Auto");
        button_apply = new CustomButton("Apply");
        button_save = new CustomButton("Save");
        button_combine = new CustomButton("Combine");

        left = new BasicArrowButton(BasicArrowButton.WEST);
        right = new BasicArrowButton(BasicArrowButton.EAST);

        image_1 = new JLabel();
        image_2 = new JLabel();

        /**
         * create checkbox and add options
         */
        morph_operations = new CheckboxGroup();
        none = new Checkbox("None", morph_operations, true);
        opening = new Checkbox("Opening", morph_operations, false);
        closing = new Checkbox("Closing", morph_operations, false);
        dilation = new Checkbox("Dilation", morph_operations, false);
        erosion = new Checkbox("Erosion", morph_operations, false);
        greyScale = new Checkbox("Grey scale", morph_operations, false);
        invert = new Checkbox("Binary invert", morph_operations, false);

        /**
         * create button panel for the card layout and add buttons
         */
        panel_au_ma.setLayout(new FlowLayout());
        panel_au_ma.add(button_none);
        panel_au_ma.add(button_manual);
        panel_au_ma.add(button_auto);

        /**
         * create choice and add objects
         */
        manual = new Choice();
        manual.add("average RGB value");
        manual.add("binary");
        manual.add("red pixels");
        manual.add("blue pixels");
        manual.add("green pixels");
        manual.add("saturation hsb");
        panel_manual.setLayout(new GridLayout(3,1,0,20));
        GridBagConstraints d = new GridBagConstraints();
        panel_manual.add(manual);

        /**
         * create scrollbar
         */
        scroll = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 200);
        scroll.setSize(150,10);
        panel_manual.add(scroll);
        text_4.setHorizontalAlignment(SwingConstants.CENTER);
        panel_manual.add(text_4);

        /**
         * create choice and add objects
         */
        auto = new Choice();
        auto.add("auto threshold");
        auto.add("red pixels");
        auto.add("blue pixels");
        auto.add("green pixels");
        auto.add("saturation hsb");
        panel_auto.add(auto);

        /**
         * create a card layout and add options
         */
        card = new CardLayout();
        panel_threshold.setLayout(card);
        panel_threshold.add(panel_none, "None");
        panel_threshold.add(panel_manual, "Manual");
        panel_threshold.add(panel_auto, "Auto");

        /**
         * create a button panel and add buttons
         */
        panel_app_save_combine.setLayout(new FlowLayout());
        panel_app_save_combine.add(button_apply);
        panel_app_save_combine.add(button_combine);
        panel_app_save_combine.add(button_save);

        /**
         * set frame options
         */
        frame.setSize(1400, 700);
        frame.setTitle("Morphology Image Analyzer");
        text_1.setFont(new Font("Arial", Font.BOLD, 15));

        /**
         * create a layout of options
         */
        panel_options.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0,40,0);
        panel_options.add(button_load, c);
        c.gridy = 1;
        c.insets = new Insets(0, 0,10,0);
        panel_options.add(text_2, c);
        c.gridy = 2;
        panel_options.add(none, c);
        c.gridy = 3;
        panel_options.add(opening, c);
        c.gridy = 4;
        panel_options.add(closing, c);
        c.gridy = 5;
        panel_options.add(dilation, c);
        c.gridy = 6;
        panel_options.add(erosion, c);
        c.gridy = 7;
        panel_options.add(greyScale, c);
        c.gridy = 8;
        c.insets = new Insets(0, 0,40,0);
        panel_options.add(invert, c);
        c.gridy = 9;
        c.insets = new Insets(0, 0,10,0);
        panel_options.add(text_3, c);
        c.gridy = 10;
        panel_options.add(panel_au_ma, c);
        c.gridy = 11;
        panel_options.add(panel_threshold, c);
        c.gridy = 12;
        c.weighty = 0;
        c.insets = new Insets(30, 0,0,0);
        panel_options.add(panel_app_save_combine, c);

        /**
         * read the image and preset it in the panel
         */
        BufferedImage start_image = null;
        try
        {
            start_image = ImageIO.read(getClass().getResource("/resources/images/start.png"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        image_1.setIcon(new ImageIcon(start_image));
        image_2.setIcon(new ImageIcon(start_image));

        panel_images.setLayout(new FlowLayout());
        panel_images.add(image_1);
        panel_images.add(image_2);

        /**
         * arrow panel
         */
        panel_arrow.setLayout(new BorderLayout());
        panel_arrow.add(left, BorderLayout.WEST);
        panel_arrow.add(right, BorderLayout.EAST);

        /**
         * image panel
         */
        panel_imag_arr.setLayout(new BoxLayout(panel_imag_arr, BoxLayout.Y_AXIS));
        panel_imag_arr.add(Box.createRigidArea(new Dimension(1, 20)));
        panel_imag_arr.add(panel_images);
        panel_imag_arr.add(Box.createRigidArea(new Dimension(1, 20)));
        panel_imag_arr.add(panel_arrow);

        /**
         * add everything to the main panel and to the frame
         */
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setLayout(new BorderLayout());
        panel.add(text_1, BorderLayout.NORTH);
        panel.add(panel_options, BorderLayout.WEST);
        panel.add(panel_imag_arr, BorderLayout.EAST);
        frame.add(panel);

        /**
         * action, item and adjustment listeners
         */
        button_load.addActionListener(this);

        none.addItemListener(this);
        opening.addItemListener(this);
        closing.addItemListener(this);
        dilation.addItemListener(this);
        erosion.addItemListener(this);
        greyScale.addItemListener(this);
        invert.addItemListener(this);

        button_none.addActionListener(this);
        button_manual.addActionListener(this);
        button_auto.addActionListener(this);
        manual.addItemListener(this);
        auto.addItemListener(this);

        scroll.addAdjustmentListener(this);

        button_apply.addActionListener(this);
        button_save.addActionListener(this);
        button_combine.addActionListener(this);
        left.addActionListener(this);
        right.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * main
     */
    public static void main(String args[])
    {
        SwingMenu menu = new SwingMenu();
        menu.Algorithm();
    }

    @Override
    /**
     * what happens if an action is performed
     * @param ae the action e.g. button was pressed
     */
    public void actionPerformed(ActionEvent ae)
    {
        Object z1 = ae.getSource();
        if ( z1 == button_load )
        {
            JFileChooser fc = new JFileChooser();
            BufferedImage image = null;
            /**
             * choose file
             */
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                image = null;

                try
                {
                    image = ImageIO.read(file);
                    img1.modifyImageObject(1300, 1300, image);
                    img2.modifyImageObject(1300, 1300, image);

                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
            /**
             * change image panel to match the chosen image
             */
            Image cropped_image = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            image_1.setIcon(new ImageIcon(cropped_image));
            image_2.setIcon(new ImageIcon(cropped_image));
            panel.repaint();
        }
        /**
         * different thresholds
         */
        else if ( z1 == button_none )
        {
            card.show(panel_threshold, "None");
            autoLvl = "none";
        }
        else if ( z1 == button_manual )
        {
            card.show(panel_threshold, "Manual");
            autoLvl = "manual";
            thresholdType = "average RGB value";
        }
        else if ( z1 == button_auto )
        {
            card.show(panel_threshold, "Auto");
            autoLvl = "auto";
            thresholdType = "auto threshold";
        }
        /**
         * apply filters and changes
         */
        else if ( z1 == button_apply )
        {
            applyOperation();
        }
        /**
         * combine two images
         */
        else if ( z1 == button_combine )
        {
            JFileChooser fc = new JFileChooser();
            BufferedImage image = null, cropped_image = null;
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                image = null;

                try
                {
                    image = ImageIO.read(file);
                    img3.modifyImageObject(1300, 1300, image);


                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
            img2.combinedWithImage(img3, true);
            img2.combinedWithImage(img3, true);
            BufferedImage f1 = null;
            f1 = img2.getImage();

            Image cropped_image1 = f1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);

            image_2.setIcon(new ImageIcon(cropped_image1));

            panel.repaint();
        }
        /**
         * save image
         */
        else if ( z1 == button_save )
        {
            img2.writeImage("src/resources/saved/saved_image"+saveIterator+".jpg");
            saveIterator ++;
        }
        /**
         * go back to the previous picture
         */
        else if ( z1 == left )
        {
            img2.modifyImageObject(1300, 1300, img1.getImage());
            img1.modifyImageObject(1300, 1300, previous.getImage());
            BufferedImage f1 = null;
            BufferedImage f2 = null;
            f1 = img1.getImage();
            f2 = img2.getImage();
            Image cropped_image1 = f1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            Image cropped_image2 = f2.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            image_1.setIcon(new ImageIcon(cropped_image1));
            image_2.setIcon(new ImageIcon(cropped_image2));
            panel.repaint();
        }
        else if ( z1 == right )
        {
        }
    }
    /**
     * what happens if a state of an item is changed
     * @param ie the state was changed e.g. another box was checked
     */
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        Object z2 = ie.getItem();
        /**
         * different morphological operations
         */
        if( z2 == none )
        {
            morf = "none";
            System.out.println("none");
        }
        else if ( z2 == opening.getLabel() )
        {
            System.out.println("opening");
            morf = "opening";
        }
        else if( z2 == closing.getLabel() )
        {
            System.out.println("clos");
            morf = "closing";
        }
        else if( z2 == dilation.getLabel() )
        {
            System.out.println("dila");
            morf = "dilation";
        }
        else if( z2 == erosion.getLabel() )
        {
            System.out.println("eros");
            morf = "erosion";
        }
        else if( z2 == greyScale.getLabel() )
        {

            morf = "greyScale";
        }
        else if( z2 == invert.getLabel() )
        {

            morf = "invert";
        }
        /**
         * different types of thresholds
         */
        else if( autoLvl == "manual" )
        {

            String z3 = manual.getSelectedItem();

            autoLvl = "manual";
            if( z3 == "average RGB value" )
            {

                thresholdType = z3;
            }
            else if( z3 == "binary" )
            {

                thresholdType = z3;
            }
            else if( z3 == "red pixels" )
            {

                thresholdType = z3;
            }
            else if( z3 == "blue pixels" )
            {
                thresholdType = z3;
            }
            else if( z3 == "green pixels" )
            {
                thresholdType = z3;
            }
            else if( z3 == "saturation hsb" )
            {
                thresholdType = z3;;
            }

        }
        else if( autoLvl == "auto" )
        {
            String z3 = auto.getSelectedItem();

            autoLvl = "auto";
            if( z3 == "auto threshold" )
            {
                thresholdType = z3;
            }
            else if( z3 == "red pixels" )
            {
                thresholdType = z3;
            }
            else if( z3 == "blue pixels" )
            {
                thresholdType = z3;
            }
            else if( z3 == "green pixels" )
            {
                thresholdType = z3;
            }
            else if( z3 == "saturation hsb" )
            {
                thresholdType = z3;
            }
        }
        else if (autoLvl == "none")
        {
            autoLvl = "none";
        }
    }
    /**
     * what happens if an adjustment as made
     * @param ave the scrollbar was adjusted
     */
    @Override
    public void adjustmentValueChanged(AdjustmentEvent ave)
    {
        msg = "Value: " + scroll.getValue();
        text_4.setText(msg);
        panel.repaint();

    }
    /**
     * apply filters and options, generate and display the new images
     */
    public void applyOperation() {
        previous.modifyImageObject(1300, 1300, img1.getImage());
        img1.modifyImageObject(1300, 1300, img2.getImage());

        switch (autoLvl){
            case "auto":
                switch (thresholdType){
                    case "auto threshold":
                        Threshold.autoThreshold(img2);
                        break;
                    case "red pixels":
                        Threshold.autoThreshold_usingRedValueOfPixels(img2);
                        break;
                    case "blue pixels":
                        Threshold.autoThreshold_usingBlueValueOfPixels(img2);
                        break;
                    case "green pixels":
                        Threshold.autoThreshold_usingGreenValueOfPixels(img2);
                        break;
                    case "saturation hsb":
                        Threshold.autoThreshold_usingSaturationOfPixelsInHSB(img2);
                        break;
                    default:
                        System.out.println("nic w auto");
                }
                break;
            case "manual":
                switch (thresholdType){
                    case "average RGB value":
                        Threshold.averageOfRGBValue(img2, scroll.getValue());
                        break;
                    case "binary":
                        Threshold.toBinary(img2, scroll.getValue());
                        break;
                    case "red pixels":
                        Threshold.redPixel(img2, scroll.getValue());
                        break;
                    case "blue pixels":
                        Threshold.bluePixel(img2, scroll.getValue());
                        break;
                    case "green pixels":
                        Threshold.greenPixel(img2, scroll.getValue());
                        break;
                    case "saturation hsb":
                        Threshold.saturationHSB(img2, scroll.getValue());
                        break;
                    default:
                        System.out.println("nic w maual");
                }
                break;
            case "none":
                switch (morf){
                    case "opening":
                        Opening.binaryImage(img2, true, false);
                        break;
                    case "closing":
                        Closing.binaryImage(img2);
                        break;
                    case "dilation":
                        Dilation.binaryImage(img2, false);
                        break;
                    case "erosion":
                        Erosion.binaryImage(img2, true);
                        break;
                    case "none":
                        break;
                    case "greyScale":
                        toGrey.RGBtoGREY(img2);
                        break;
                    case "invert":
                        img2.binaryNegative();
                    default:
                        System.out.println("nic w morf");
                }
                break;
            default:
                System.out.println("nic w lvl");
        }
        /**
         * change image labels to match the options chosen
         */
        BufferedImage f1 = null;
        BufferedImage f2 = null;
        f1 = img1.getImage();
        f2 = img2.getImage();
        Image cropped_image1 = f1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        Image cropped_image2 = f2.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        image_1.setIcon(new ImageIcon(cropped_image1));
        image_2.setIcon(new ImageIcon(cropped_image2));
        panel.repaint();
    }
}