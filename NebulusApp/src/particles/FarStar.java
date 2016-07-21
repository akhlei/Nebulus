/*FAR STAR
Background stars, mainly decorative. 
 */

package particles;

import java.awt.*;
import java.awt.geom.*;

import primary.Util;

public class FarStar extends DistStar{
	private Ellipse2D.Double flare;
	private Line2D.Double line1, line2, line3, line4;  //lines used to create the light "flare"
	
	//constructor for the FarStar class 
	public FarStar(double x, double y){   //int c will determine color options
		super(x,y);
		dia= 10;
		scale = Util.random(0.15, 0.30); //adjusts scale randomly to add
		//some variance in graphical display
		
		core = new Ellipse2D.Double();
		glow = new Ellipse2D.Double();
		flare = new Ellipse2D.Double();
		line1 = new Line2D.Double();
		line2 = new Line2D.Double();
		line3 = new Line2D.Double();
		line4 = new Line2D.Double();
		
		glowColor = new Color(18, 188, 255);
		coreColor = new Color(35, 233, 255);
	}
	
	//instantiates the graphical object elements and prepares them for the drawMe class
	private void setForms(){
		core.setFrame(-dia+4, -dia+4, dia*.7, dia*.7);
		glow.setFrame(-dia+.75, -dia+.75, dia*1.25, dia*1.25);
		flare.setFrame(-dia+.25, -dia+.25, dia*1.45, dia*1.45);
		//vertical hortizontal lines
		line1.setLine(16, -dia/4, -20, -dia/4);  //numbers for length, dia for position
		line2.setLine(-dia/4, -25, -dia/4, 19);	 
		//diagonal lines
		line3.setLine(10.25, -12.5, -14.25, 11.5);
		line4.setLine(-14.25, -16.5, 10.25, 11.5);
	}
	
	@Override
	//main graphical display class 
	public void drawMe(Graphics2D g2){
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		setForms();
		g2.setColor(glowColor);
		g2.fill(flare);
		g2.setColor(coreColor);
		g2.fill(glow);
		g2.setColor(Color.white);
		g2.fill(core);
		g2.draw(line1);		//lines extend the flare points to improve "twinkle" effect
		g2.draw(line2);
		g2.draw(line3);
		g2.draw(line4);
		AffineTransform t2 = g2.getTransform();
		g2.translate(0.25, 0);
		int[] triX1 = {-5, -3, 0};  //top flare point
		int[] triY1 = {-4, -25, -4};
		g2.fillPolygon(triX1, triY1, 3);
		int[] triX2 = {-5, -3, 0};  //bottom flare point 
		int[] triY2 = {-1, 20, -1};
		g2.fillPolygon(triX2, triY2, 3);
		int [] triX3 = {0, -26, 0};   //left flare point 
		int [] triY3 = {-5, -2, 1}; 
		g2.fillPolygon(triX3, triY3, 3);
		int [] triX4 = {-1, 23, -1};  //right flare point
		int [] triY4 = {1, -2, -5}; 
		g2.fillPolygon(triX4, triY4, 3);
		g2.setTransform(t2);
		g2.setTransform(transform);
	}
	
	//getter for the X position
	public double getXPos() {
		return xPos;
	}
	//getter for the Y position
	public double getYPos() {
		return yPos;
	}
	
	
}
