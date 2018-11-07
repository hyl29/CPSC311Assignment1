import Model.Pixel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class PixelRGBCanvas extends Canvas {
    private int width;
    private int height;
    private List<Pixel> pixelColor;

    public PixelRGBCanvas(int width, int height, List<Pixel> pixelArray){
        this.width = width;
        this.height = height;
        this.pixelColor = pixelArray;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //red
        int i = pixelColor.size()-1;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                int red = pixelColor.get(i).getAveragePixel();
                int green = 0;
                int blue = 0;
                Color current = new Color(red, green, blue);

                g.setColor(current);
                g.drawLine(x, y, x, y);
                i = i - 1;
            }
        }

        // green
        int k = pixelColor.size()-1;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                int red = 0;
                int green = pixelColor.get(k).getAveragePixel();
                int blue = 0;
                Color current = new Color(red, green, blue);

                g.setColor(current);
                g.drawLine(x, y+height, x, y+height);
                k = k - 1;
            }
        }

        // blue
        int j = pixelColor.size()-1;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                int red = 0;
                int green = 0;
                int blue = pixelColor.get(j).getAveragePixel();
                Color current = new Color(red, green, blue);

                g.setColor(current);
                g.drawLine(x+width, y, x+width, y);
                j = j - 1;
            }
        }
    }

}
