package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import primary.Util;

public class RockPlanet extends CelestialBeing{


	public RockPlanet(double x, double y){
		super(x, y);
		scale = 0.3;
		displayImg = Util.loadImage("assets/mars.png");
		
		
		Graphics g = displayImg.getGraphics();
		g.setClip(0, 0, displayImg.getWidth(null), displayImg.getHeight(null));
	}
	
	public void drawMe(Graphics2D g2){
		AffineTransform tf2 = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(displayImg, 0, 0,null);
		g2.setTransform(tf2);
	}

}
