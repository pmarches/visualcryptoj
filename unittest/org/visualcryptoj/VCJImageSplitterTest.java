/**
 * Copyright 2013 pmarches@gmail.com
 */
package org.visualcryptoj;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.BitSet;

import javax.imageio.ImageIO;

import org.junit.Test;

public class VCJImageSplitterTest {
	VCJImageSplitter splitter = new VCJImageSplitter();

	@Test
	public void testBitSetToImageConversion(){
		BitSet bs1 = new BitSet(6);
		bs1.set(0);
		bs1.set(2);
		bs1.set(4);
		BufferedImage bi1 = splitter.bitsetToImage(bs1 , 3, 2);
		assertEquals(VCJImageSplitter.BLACK, bi1.getRGB(0, 0));
		assertEquals(VCJImageSplitter.WHITE, bi1.getRGB(1, 0));
		assertEquals(VCJImageSplitter.BLACK, bi1.getRGB(2, 0));
		assertEquals(VCJImageSplitter.WHITE, bi1.getRGB(0, 1));
		assertEquals(VCJImageSplitter.BLACK, bi1.getRGB(1, 1));
		assertEquals(VCJImageSplitter.WHITE, bi1.getRGB(2, 1));
		BitSet bs2 = splitter.imageToBitSet(bi1);
		assertEquals(bs1, bs2);
	}

	@Test
	public void testBWConversion() throws Exception {
		BufferedImage clearImage = ImageIO.read(new File("testdata/canada.png"));
		BufferedImage bwImage = splitter.convertToBlackWhite(clearImage);
		ImageIO.write(bwImage, "png", new File("canadaBW.png"));
	}
	
	@Test
	public void test() throws Exception {
		BufferedImage clearImage = ImageIO.read(new File("testdata/canada.png"));
		BufferedImage[] cipherImages = splitter.split(clearImage, 2);
		assertNotNull(cipherImages);
		assertEquals(2, cipherImages.length);

		ImageIO.write(cipherImages[0], "png", new File("cipher0.png"));
		ImageIO.write(cipherImages[1], "png", new File("cipher1.png"));
	}

	@Test
	public void testExpand() throws Exception {
		BitSet inset = new BitSet(6);
		inset.set(0);
		inset.set(2);
		inset.set(4);
		BitSet bigset = splitter.expandDiagonal(inset, 3, 2, 2);
		assertTrue(bigset.get(0));
		assertFalse(bigset.get(1));
		assertFalse(bigset.get(2));
		assertTrue(bigset.get(3));
		assertTrue(bigset.get(4));
		assertFalse(bigset.get(5));
		assertFalse(bigset.get(6));
		assertTrue(bigset.get(7));
		assertTrue(bigset.get(8));
		assertFalse(bigset.get(9));
		assertFalse(bigset.get(10));
		assertTrue(bigset.get(11));
		assertFalse(bigset.get(12));
		assertTrue(bigset.get(13));
		assertTrue(bigset.get(14));
		assertFalse(bigset.get(15));
		assertFalse(bigset.get(16));
		assertTrue(bigset.get(17));
		assertTrue(bigset.get(18));
		assertFalse(bigset.get(19));
		assertFalse(bigset.get(20));
		assertTrue(bigset.get(21));
		assertTrue(bigset.get(22));
		assertFalse(bigset.get(23));
	}

}
