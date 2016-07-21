////MOON 
//A (later) interactive Moon object which will animate when clicked
//Single entity within the space. 

package entities;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;

import primary.Util;

import java.awt.geom.Ellipse2D;

public class Moon extends CelestialBeing{ 
		
	public Moon (double x, double y){
		super(x,y);
		scale = 0.07;
		displayImg = Util.loadImage("assets/moon.png");
		
		Graphics g = displayImg.getGraphics();
		g.setClip(0, 0, displayImg.getWidth(null), displayImg.getHeight(null));
	}

	
	public void drawMe(Graphics2D g2){
		AffineTransform tf = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(displayImg, 0 , 0,null);
		g2.setTransform(tf);

	}
}
