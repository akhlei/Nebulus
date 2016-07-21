//Particle to be used in a particle system that will simulate a galaxy spinning
//A single "cloud" like transparent circle

package particles;
import java.awt.*;
import java.awt.geom.*;

import primary.*;


public class Nebula extends GravityObject{ 
	private double dia;         //diameter used as a base for graphic scale/position 
	private double scale = 1;  
	private double scheme;    //differentiates between color schemes
	private int alpha;		//opacity of colors 
	
	private double life; 		//length of time for life
	private double angle; 	//angle particle moves at
	private Color primeColor;		//color fields
	private Color hiliteColor; 
		
	private Ellipse2D.Double cloud;	//layering particles 
	private Ellipse2D.Double glow;
	private Ellipse2D.Double outer;
	
	
	//testing constructor 
	public Nebula(double x, double y, double a, double s){
		xPos = x;
		yPos = y;
		angle = a;
		xSpd = s;
		ySpd = s;
		life = Util.random(110, 160);	
		dia = Util.random(14, 21);
		cloud = new Ellipse2D.Double();
		glow = new Ellipse2D.Double();
		outer = new Ellipse2D.Double();
		alpha = 20;
		primeColor = new Color (100, 12, 120, alpha);
		hiliteColor = new Color (175, 21, 150, alpha); 
	}

	
	@Override
	public void update(Graphics2D g2){
		life --;
		if (life > 0) {
			move();
			drawMe(g2);
		}
		if (life < 90) alpha --;
	}
	
	@Override
	public void move(){
		double radAngle = Math.toRadians(angle);
		radAngle += 0.5; 
		xPos += Math.cos(radAngle) * xSpd; 
		yPos -= Math.sin(radAngle) * ySpd;	
	}
	
	@Override
	public void drawMe(Graphics2D g2){
		outer.setFrame(-dia/6, -dia/6, dia+dia/3, dia+dia/3);
		cloud.setFrame(0, 0, dia, dia);
		glow.setFrame(dia/3.5, dia/3.5, dia/2.5, dia/2.5);
		AffineTransform tf = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.setColor(primeColor);
		g2.fill(outer);
		g2.setColor(hiliteColor);
		g2.fill(cloud);
		g2.fill(glow);
		g2.setTransform(tf);
	}
	public double getX(){
		return xPos;
	}
	public double getY(){
		return yPos;
	}
	public double getLife(){
		return life;
	}
	public void setLife(double l){
		life = l;
	}
}
