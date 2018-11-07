import Model.Pixel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class BmpProcessor {
    public static void main(String[] argv) throws FileNotFoundException, IOException {


        //Open File Dialog Box
        //http://www.java2s.com/Tutorial/Java/0240__Swing/InputPopUps.htm
        JFrame frame = new JFrame();
        String fileLocation = JOptionPane.showInputDialog(frame, "Enter File Name: ");
        System.out.println(fileLocation);

        //String fileLocation = "data/Q3_sample_1.bmp";
        File file = new File(fileLocation);
        int fileSize = 0;
        List<Pixel> pixelList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file)) {

            // Get file size
            fileSize = fis.available();
            // Store all the bmp file header
            int[] bmpHeader = new int[14];

            for (int i = 0; i < 14; i++) {
                bmpHeader[i] = fis.read();
                //System.out.println("i am byte " + i + " " + bmpHeader[i]);
            }

            // Store all the dib file header
            int dibSize = fis.read();
            int[] dibHeader = new int[dibSize+14];
            dibHeader[14] = dibSize;
            //System.out.println("i am byte " + 14 + " " + dibHeader[14]);

            for (int i = 15; i < dibSize+14; i++) {
                dibHeader[i] = fis.read();
                //System.out.println("i am byte " + i + " " + dibHeader[i]);
            }

            // obtain data of width and height from bmp's dibHeader
            int width = 0;
            int height = 0;

            // algorithm to get data while using little endian form
            for (int i = 0; i < 2; i++) {
                for (int j = 18; j < 25; j+=4 ) {
                    String hex1 = Integer.toHexString(dibHeader[j]);
                    String hex2 = Integer.toHexString(dibHeader[j+1]);
                    String hex3 = Integer.toHexString(dibHeader[j+2]);
                    String hex4 = Integer.toHexString(dibHeader[j+3]);

                    if (hex1.equals("0")){
                        hex1 = hex1 + 0;
                    }
                    if (hex2.equals("0")){
                        hex2 = hex2 + 0;
                    }
                    if (hex3.equals("0")){
                        hex3 = hex3 + 0;
                    }
                    if (hex4.equals("0")){
                        hex4 = hex4 + 0;
                    }

                    String combined = hex4 + hex3 + hex2 + hex1;

                    int combinedLong = Integer.valueOf(combined,16);
                    if (i == 0){
                        width = combinedLong;
                    }
                    else{
                        height = combinedLong;
                    }
                }
            }
            //System.out.println("width " + width + " height " + height);

            // Store the pixel array
            int[] pxData = new int[fileSize+14];
            for (int i = dibSize+14; i < fileSize; i++){
                pxData[i] = fis.read();
                //System.out.println("i am byte " + i + " " + pxData[i]);
            }

            // find how much paddings are used
            // is %3 because we are only using 24bits per pixel
            int padding = (fileSize - (14 + dibSize)) % 3;

            // putting the pixel data in to the pixel object and into pixel array
            for(int i = dibSize+14; i < fileSize-padding; i+=3){
                Pixel pixelObject = new Pixel(pxData[i], pxData[i+1], pxData[i+2]);
                pixelList.add(pixelObject);
            }

            //System.out.println(fileSize);
            //System.out.println("size " + pixelList.size());

            // THESE ARE USED WHEN PADDING IS REQUIRED
            // I HAVE NO IDEA WHAT IS GOING ON BUT WIKI HAS THE FORMULA SO IM GONNA CALCULATE IT
            // Calculating Pixel Storage row size
            double rowSize = (Math.floor((24 * width + 31) / 32) * 4);
            // Calculating Pixel Array size
            double pxArraySize = rowSize * Math.abs(height);

            // Q2.1
            JFrame frameOriginal = new JFrame();
            frameOriginal.setTitle("Original bmp");
            frameOriginal.setSize(width*5, height*5);
            PixelCanvas pc1 = new PixelCanvas(width,height,pixelList);
            frameOriginal.add(pc1);
            frameOriginal.setVisible(true);

            // Q2.2
            JFrame frameRGB = new JFrame();
            frameRGB.setTitle("RGB bmp");
            frameRGB.setSize(width*5, height*5);
            PixelRGBCanvas pc2 = new PixelRGBCanvas(width,height,pixelList);
            frameRGB.add(pc2);
            frameRGB.setVisible(true);

            // Q2.3
            JFrame frameBright = new JFrame();
            frameBright.setTitle("1.5x Brighter bmp");
            frameBright.setSize(width*5, height*5);
            PixelBrightCanvas pc3 = new PixelBrightCanvas(width,height,pixelList);
            frameBright.add(pc3);
            frameBright.setVisible(true);

            // Q2.4
            JFrame frameGray = new JFrame();
            frameGray.setTitle("Grayscale bmp");
            frameGray.setSize(width*5, height*5);
            PixelGrayCanvas pc4 = new PixelGrayCanvas(width,height,pixelList);
            frameGray.add(pc4);
            frameGray.setVisible(true);

            // Q2.5
            JFrame frameOD = new JFrame();
            frameOD.setTitle("Ordered dithering bmp");
            frameOD.setSize(width*5, height*5);
            PixelCanvas pc5 = new PixelCanvas(width,height,pixelList);
            frameOD.add(pc5);
            frameOD.setVisible(true);
        }
    }
}