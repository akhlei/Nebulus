//Utility class which is used to calculate values 
//separate from the main running calculations of the application 

package primary;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;




public class Util {
	//random intakes two parameters, low and high, and returns a value between the two
	public static double random(double low, double high) {
		return low + Math.random() * (high - low);
	}
	
	public static BufferedImage loadImage(String filename){
		BufferedImage bufferImg = null;
		try {
			bufferImg = ImageIO.read(new File(filename));
		} catch (IOException e){
			e.printStackTrace();
		}
		return bufferImg;
	}
}
