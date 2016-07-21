/*PARTICLE/SPACEDUST CLASS
this class will serve as a background "decorative" particle object (spacedust?)
and will free-fall continuously from the top of the screen like snow 
non interactive, acts just as a visual style feature */

package particles;
import java.awt.*;
import java.awt.geom.*;





public class Particle extends GravityObject{  //unsure if this class will be abstract, see which fields overlap
	private double dia; //diameter of particle 
	
	private int colorScheme; //sets to one of two colors (this could be a decorator)
	private Color coreColor; //inner high saturation and brightness color of the particle
	private Color glowColor; //outer low brightness faint glow color
	
	private Ellipse2D.Double core;  
	private  Ellipse2D.Double glow;  //shape fields
	
	//constructor class for the Particle object
	public Particle(double x, double y, double xs, double ys, int sc){
		xPos = x;
		yPos = y;
		xSpd = xs;
		ySpd = ys;  //x and y velocity 
		dia = 1.5;
		colorScheme = sc;  //determines color schemes
		
		//way to differentiate between two different color schemes from parameters
		if (colorScheme == 1){
			coreColor = new Color(225, 18, 135);
			glowColor = new Color(168, 17, 135);
		} else if (colorScheme == 2){
			coreColor = new Color(30, 210, 250);
			glowColor = new Color(25, 175, 205);
		}
		core = new Ellipse2D.Double();
		glow = new Ellipse2D.Double();
	}
	
	
	//display class which creates and displays the graphical elements of the class
	@Override
	public void drawMe(Graphics2D g2){
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(1, 1);
		core.setFrame(-dia+75, -dia+75, dia/2, dia/2);
		glow.setFrame(-dia, -dia, dia, dia);
		//drawing component
		g2.setColor(glowColor);
		g2.fill(glow);
		g2.setColor(coreColor);
		g2.fill(core);
		g2.setTransform(transform);
	}
	
	//getter for X position
	public double getXPos() {
		return xPos;
	}
	//getter for Y position 
	public double getYPos() {
		return yPos;
	}
	//getter for X velocity
	public double getXSpeed(){
		return xSpd;
	}
	//getter for Y velocity 
	public double getYSpeed(){
		return ySpd;
	}
	
}
