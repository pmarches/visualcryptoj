/**
 * Copyright 2013 pmarches@gmail.com
 */
package org.visualcryptoj;

import java.awt.image.BufferedImage;

public class SimpleImageSplitter {
	public BufferedImage[] split(BufferedImage clearImage, BufferedImage splitMask) throws Exception{
		if(clearImage.getWidth()!=splitMask.getWidth()){
			throw new Exception("Mask must have same width as the image to split");
		}
		if(clearImage.getHeight()!=splitMask.getHeight()){
			throw new Exception("Mask must have same height as the image to split");
		}

		final int width = clearImage.getWidth();
		final int height = clearImage.getHeight();
		int[] imgLine0 = new int[width];
		int[] imgLine1 = new int[width];
		int[] maskLine = new int[width];

		BufferedImage[] shards = new BufferedImage[]{
				new BufferedImage(width, height, clearImage.getType()),
				new BufferedImage(width, height, clearImage.getType()),
		};
		for (int y = 0; y < height; y++) {
		    // fetch a line of data from each image
			clearImage.getRGB(0, y, width, 1, imgLine0, 0, 1);
			splitMask.getRGB(0, y, width, 1, maskLine, 0, 1);
		    for (int x = 0; x < width; x++) {
		        int color = imgLine0[x] & 0x00FFFFFF; // mask away any alpha present
		        int mask = maskLine[x] & 0x00FFFFFF;
		        if(mask!=0){
			        imgLine0[x] = 0xFF000000 | color;
			        imgLine1[x] = 0x00FFFFFF; //blank out
		        }
		        else{
		        	imgLine0[x] = 0x00FFFFFF; //blank out
			        imgLine1[x] = 0xFF000000 | color;
		        }
		    }
		    shards[0].setRGB(0, y, width, 1, imgLine0, 0, 1);
		    shards[1].setRGB(0, y, width, 1, imgLine1, 0, 1);
		}
		
		return shards;
	}
}
