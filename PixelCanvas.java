import Model.Pixel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class PixelCanvas extends Canvas {
    private int width;
    private int height;
    private List<Pixel> pixelColor;

    public PixelCanvas(int width, int height, List<Pixel> pixelArray){
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

                int red = pixelColor.get(i).getRedPixel();
                int green = pixelColor.get(i).getGreenPixel();
                int blue = pixelColor.get(i).getBluePixel();
                Color current = new Color(red, green, blue);

                g.setColor(current);
                g.drawLine(x, y, x, y);
                i = i - 1;
            }
        }
    }

}
