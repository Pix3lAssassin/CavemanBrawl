package com.pixelcross.cavemanbrawl.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static Image convertToFxImage(BufferedImage image, int scaleFactor) {
	    WritableImage wr = null;
	    if (image != null) {
	        wr = new WritableImage(image.getWidth(), image.getHeight());
	        PixelWriter pw = wr.getPixelWriter();
	        for (int x = 0; x < image.getWidth(); x++) {
	            for (int y = 0; y < image.getHeight(); y++) {
	                pw.setArgb(x, y, image.getRGB(x, y));
	            }
	        }
	    }

	    return resample(new ImageView(wr).getImage(), scaleFactor);
	}

	public static Image resample(Image input, int scaleFactor) {
	    final int W = (int) input.getWidth();
	    final int H = (int) input.getHeight();
	    final int S = scaleFactor;
	    
	    WritableImage output = new WritableImage(
	      W * S,
	      H * S
	    );
	    
	    PixelReader reader = input.getPixelReader();
	    PixelWriter writer = output.getPixelWriter();
	    
	    for (int y = 0; y < H; y++) {
	      for (int x = 0; x < W; x++) {
	        final int argb = reader.getArgb(x, y);
	        for (int dy = 0; dy < S; dy++) {
	          for (int dx = 0; dx < S; dx++) {
	            writer.setArgb(x * S + dx, y * S + dy, argb);
	          }
	        }
	      }
	    }
	    
	    return output;
	  }
}
