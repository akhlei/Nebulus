//CelestialBeing is an abstract class which acts as a superclass to objects which 
//are unique in the environment 
//(ie a single version, not highly repeated like Particles or Asteroids)
//most are primarily image based objects and do not utilize java based graphics

package entities;
import java.awt.*;


public abstract class CelestialBeing{
	//position related fields 
	protected double xPos, yPos;
	protected Image displayImg; 
	
	//size related fields 
	protected int dia;
	protected double scale;
	
	public CelestialBeing(double x, double y){
		xPos = x;
		yPos = y;
	}
	
	public abstract void drawMe(Graphics2D g2);
	
}
