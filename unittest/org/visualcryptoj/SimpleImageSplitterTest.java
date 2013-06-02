/**
 * Copyright 2013 pmarches@gmail.com
 */
package org.visualcryptoj;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class SimpleImageSplitterTest {
	SimpleImageSplitter splitter = new SimpleImageSplitter();
	
	@Test
	public void testSplit() throws Exception {
		BufferedImage clearImage = ImageIO.read(new File("testdata/canada.png"));
		BufferedImage splitMask = new BufferedImage(clearImage.getWidth(), clearImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		int diagonal=Math.min(clearImage.getWidth(), clearImage.getHeight());
		for(int y=0; y<diagonal; y++){
			for(int x=0; x<y; x++){
				splitMask.setRGB(x, y, 0x00FFFFFF);
			}
		}
		BufferedImage[] shards = splitter.split(clearImage, splitMask);
		assertNotNull(shards);
		assertEquals(2, shards.length);

		ImageIO.write(shards[0], "png", new File("simpleSplit0.png"));
		ImageIO.write(shards[1], "png", new File("simpleSplit1.png"));
	}

}
