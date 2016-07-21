package entities;

import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;

import primary.Util;

import java.awt.Graphics.*;

public class GasGiant extends CelestialBeing{

	
	public GasGiant(double x, double y) {
		super(x, y);
		scale = 0.21;
		displayImg = Util.loadImage("assets/jupiter.png");
		
		Graphics g = displayImg.getGraphics();
		g.setClip(0, 0, displayImg.getWidth(null), displayImg.getHeight(null));
	}
	
	public void drawMe(Graphics2D g2){
		AffineTransform tf2 = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(displayImg, 0 , 0,null);
		g2.setTransform(tf2);
	}

}
