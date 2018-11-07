
import Model.Pixel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class PixelBrightCanvas extends Canvas {
    private int width;
    private int height;
    private List<Pixel> pixelColor;

    public PixelBrightCanvas(int width, int height, List<Pixel> pixelArray){
        this.width = width;
        this.height = height;
        this.pixelColor = pixelArray;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int i = pixelColor.size()-1;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                int red = pixelColor.get(i).getRedPixel()*3/2 ;
                if (red > 255){
                    red = 255;
                }
                int green = pixelColor.get(i).getGreenPixel()*3/2;
                if (green > 255){
                    green = 255;
                }
                int blue = pixelColor.get(i).getBluePixel()*3/2;
                if (blue > 255){
                    blue = 255;
                }
                Color current = new Color(red, green, blue);

                g.setColor(current);
                g.drawLine(x, y, x, y);
                i = i - 1;
            }
        }
    }

}
