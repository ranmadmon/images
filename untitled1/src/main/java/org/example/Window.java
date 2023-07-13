package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.stream.IntStream;

public class Window extends JFrame {
    public Window () {
        try {
            URL jetImage = getClass().getClassLoader().getResource("jet.jpg");
            if (jetImage != null) {
                BufferedImage bufferedImage = contrast(ImageIO.read(jetImage), 50);
                this.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
                int color = bufferedImage.getRGB(bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
                System.out.println("Width: " + bufferedImage.getWidth());
                System.out.println("Height: " + bufferedImage.getHeight());
                IntStream.range(0, bufferedImage.getWidth()).forEach(x -> {
                    IntStream.range(0, bufferedImage.getHeight()).forEach(y -> {
                        if (isRedish(new Color(bufferedImage.getRGB(x, y)))) {
                            System.out.println("(" + x + " , " + y + ")");
                        }
                    });
                });
                Color colorObject = new Color(color);
                System.out.println("RED:" + colorObject.getRed());
                System.out.println("GREEN: " + colorObject.getGreen());
                System.out.println("BLUE: " + colorObject.getBlue());
                JLabel label = new JLabel(new ImageIcon(bufferedImage));
                this.add(label);
            } else {
                System.out.println("Cannot find!");
            }
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBlackish (Color color) {
        return color.getRed() + color.getGreen() + color.getBlue() < 50;
    }

    public boolean isRedish (Color color) {
        return color.getRed() > 160 && color.getGreen() < 100 && color.getBlue() < 100;
    }

    public BufferedImage mirror(BufferedImage original) {
        BufferedImage processed = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight() - 1; i++) {
            for (int j = 0; j < original.getWidth() - 1; j++) {
                processed.setRGB(i, j, original.getRGB(original.getWidth() - i - 1, j));
            }
        }
        return processed;
    }

    public BufferedImage grayScale (BufferedImage original) {
        BufferedImage processed = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight() - 1; i++) {
            for (int j = 0; j < original.getWidth() - 1; j++) {
                Color color = new Color(original.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int average = (red + green + blue) / 3;
                Color newColor = new Color(average, average, average);
                processed.setRGB(i, j, newColor.getRGB());
            }
        }
        return processed;

    }

    public BufferedImage shuffle (BufferedImage original) {
        BufferedImage processed = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight() - 1; i++) {
            for (int j = 0; j < original.getWidth() - 1; j++) {
                Color color = new Color(original.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(blue, green, red);
                processed.setRGB(i, j, newColor.getRGB());
            }
        }
        return processed;
    }


    public BufferedImage negative (BufferedImage original) {
        BufferedImage processed = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight() - 1; i++) {
            for (int j = 0; j < original.getWidth() - 1; j++) {
                Color color = new Color(original.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(255 - red, 255 - green, 255  - blue);
                processed.setRGB(i, j, newColor.getRGB());
            }
        }
        return processed;

    }



    public BufferedImage contrast (BufferedImage original, int by) {
        BufferedImage processed = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight() - 1; i++) {
            for (int j = 0; j < original.getWidth() - 1; j++) {
                Color color = new Color(original.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(itensify(red, by), itensify(green, by), itensify(blue, by));
                processed.setRGB(i, j, newColor.getRGB());
            }
        }
        return processed;

    }

    public int itensify (int original, int by) {
        if (original > 128) {
            original *= ((100 + by) / 100);
        } else {
            original *= ((100 - by) / 100);
        }
        return Math.min(255, original);

    }

}
