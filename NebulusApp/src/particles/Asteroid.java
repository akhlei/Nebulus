/*
 * An Asteroid class which occasionally flies in from the edge of the screen
 * If it encounters another Asteroid object or is clicked on by the user
 * It will explode and produce an explosion effect
 */
package particles;
import java.awt.*;
import java.awt.geom.*;

import primary.*;

public class Asteroid extends GravityObject{
	private double scale;		//scales image and used for collision
	//private double rot;		//rotation angle to vary the image look
	private GeneralPath astaPath;
	private GeneralPath shadePath;
	
	private double astaH;    //height of the asteroid for mouse click detection
	private double astaW;    //width of the asteroid for mouse click detection
	private Color coreColor;    //main color
	private Color shadeColor;   //shading relative to main color
	private Color lineColor;		//dark color for outlines
	
	public Asteroid(double x, double y, double xs, double ys, double sc){
		xPos = x;
		yPos = y;
		xSpd= xs;
		ySpd = ys; 
		scale = sc;
		//rot = Util.random(0, 359);
		//grey shades for asteroid colors 
		coreColor = new Color(115, 130, 155);
		lineColor = new Color(70,80,95);
		//fields for size determination, used in collision and click detection
		astaW = 50*scale;
		astaH = 50*scale;    //value from largest number in respective point array below
	}
	
	public boolean checkMouseBound(double x, double y){
		boolean bound = false;
		if (x > xPos && x < xPos + astaW && y > yPos && y < yPos + astaH){
			bound = true;
		}
		
		return bound;
	}
	
	public boolean detectCollision(Asteroid otherAsta) {
		boolean collision = false;
		if (Math.abs(xPos - otherAsta.xPos) < ((astaW / 2 - 1.5 ) + (otherAsta.astaW / 2 + 1.5))
				&& Math.abs(yPos - otherAsta.yPos) < (astaH/ 2 - 2 + otherAsta.astaH / 2 + 2)) {
			collision = true;

		}
		return collision;
	}
	
	// method to set up the path based polygon x and y coordinate points
	private void setForms(){
		int[] xPoints = {0, 4, 8, 21, 28, 35, 50, 50, 37, 20, 12};
		int[] yPoints = {15, 30, 35, 38, 50, 50, 32, 15, 5, 4, 12};
		
		int[] xShade = {21, 28, 35, 50, 50, 37, 32, 39, 33, 34, 26};
		int[] yShade = {38, 50, 50, 32, 15, 5, 12, 24, 29, 32, 40};
		
		//define the lines from which the asteroid shape is created 
		astaPath = new GeneralPath();
		astaPath.moveTo(xPoints[0], yPoints[0]);  //star the path at first position of arrays
		for (int index = 1; index < xPoints.length; index ++) {
			astaPath.lineTo(xPoints[index], yPoints[index]);
		}
		astaPath.closePath();	
		
		//define the lines from which the asteroid shading is created
		shadePath = new GeneralPath();
		shadePath.moveTo(xShade[0], yShade[0]);
		for (int index = 1; index < xShade.length; index ++){
			shadePath.lineTo(xShade[index], yShade[index]);
		}
		shadePath.closePath();
	}
	
	@Override
	public void drawMe(Graphics2D g2){
		AffineTransform tf2 = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);     //make the image reverse once going other way  
		setForms();
		//remaining lines to be removed save the Transform, once image is implemented 
		g2.setColor(coreColor); 
		g2.fill(astaPath);
		g2.setColor(lineColor);
		g2.draw(astaPath);
		g2.setColor(shadeColor);
		g2.fill(shadePath);
		g2.setColor(lineColor);
		g2.draw(shadePath);
		g2.setColor(Color.BLACK);
		g2.draw(astaPath);
		g2.setTransform(tf2);
	}
	
	public double getXPos(){
		return xPos;
	}
	public double getYPos(){
		return yPos;
	}
	
	public double getScale(){
		return scale;
	}
	
	public double getH(){
		return astaH;
	}
	
	public double getW(){
		return astaW;
	}
}
