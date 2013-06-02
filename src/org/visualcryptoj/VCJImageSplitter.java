/**
 * Copyright 2013 pmarches@gmail.com
 */
package org.visualcryptoj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.BitSet;

public class VCJImageSplitter {
	static final int WHITE = -1;
	static final int BLACK = -16777216;

	public BufferedImage[] split(BufferedImage inputImage, int scale) {
		//Convert input image to B&W
		BufferedImage clearBWImage = convertToBlackWhite(inputImage);

		//Convert to bitset & encrypt
		int nbPixel = clearBWImage.getWidth()*clearBWImage.getHeight();
		BitSet cipherBits=imageToBitSet(clearBWImage);
		BitSet randomBits=newRandomBitSet(nbPixel);
		cipherBits.xor(randomBits);
		
		//Expand image to 4x4 pixels
		cipherBits = expandDiagonal(cipherBits, clearBWImage.getWidth(), clearBWImage.getHeight(), scale);
		randomBits = expandDiagonal(randomBits, clearBWImage.getWidth(), clearBWImage.getHeight(), scale);
		
		BufferedImage outputImages[]=new BufferedImage[]{
				bitsetToImage(cipherBits, inputImage.getWidth()*scale, inputImage.getHeight()*scale),
				bitsetToImage(randomBits, inputImage.getWidth()*scale, inputImage.getHeight()*scale)
		};
		return outputImages;
	}

	public BufferedImage convertToBlackWhite(BufferedImage inputImage) {
		BufferedImage clearBWImage=new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g2d = clearBWImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.dispose();

        return clearBWImage;
	}

	BitSet newRandomBitSet(int size) {
		SecureRandom random = new SecureRandom();
		BitSet randomBits = new BitSet(size);
		for(int i=0; i<size; i++){
			randomBits.set(i, random.nextBoolean());
		}
		return randomBits;
	}

	BufferedImage bitsetToImage(BitSet cipherBits, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		int pixelIndex=0;
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if(cipherBits.get(pixelIndex)){
					bi.setRGB(x, y, BLACK);
				}
				else{
					bi.setRGB(x, y, WHITE);
				}
				pixelIndex++;
			}
		}
		return bi;
	}

	BitSet imageToBitSet(BufferedImage clearBWImage) {
		BitSet bs = new BitSet(clearBWImage.getWidth()*clearBWImage.getHeight());
		int pixelIndex=0;
		for(int y=0; y<clearBWImage.getHeight(); y++){
			for(int x=0; x<clearBWImage.getWidth(); x++){
				int rgb = clearBWImage.getRGB(x, y);
				bs.set(pixelIndex, rgb!=WHITE);
				pixelIndex++;
			}
		}
		return bs;
	}
	
	public BitSet expandDiagonal(BitSet inset, int width, int heigth, int scale){
		int originalNbPixel=width*heigth;
		BitSet big = new BitSet(width*heigth*scale*scale);
		for(int i=0; i<originalNbPixel; i++){
			int smallx=i%width;
			int smally=i/width;
			int bigWidth=scale*width;
			int firstBigPixel=smallx*scale+smally*bigWidth*scale;
			if(inset.get(i)){
				for(int j=0; j<scale; j++){ //Left to rigth, downward diagonal
					int secondBigPixel=firstBigPixel+j*bigWidth+j;
					big.set(secondBigPixel, true);
				}
			}
			else{
				for(int j=0; j<scale; j++){ //Left to rigth, upward diagonal
					int secondBigPixel=firstBigPixel+j*bigWidth+(scale-1)-j;
					big.set(secondBigPixel, true);
				}
			}
		}
		return big;
	}

}
