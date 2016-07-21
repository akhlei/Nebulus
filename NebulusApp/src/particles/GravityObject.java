//GravityObject is an abstract class used for inclusion polymorphism of several
//subclasses which will all be able to be pulled/moved by a Black Hole event, which draws
//objects towards the middle of the screen. 

package particles;

import java.awt.Graphics2D;

import primary.*;

public abstract class GravityObject {
	protected double xPos, yPos;  //positional fields
	protected double xSpd, ySpd;  //velocity 
	protected double xAcc = 0; 
	protected double yAcc = 0; //acceleration
	
	//a physics/acceleration dependent on state in SpacePanel
	public void blackHolePull() {				
		xSpd += xAcc;		//acceleration is added to speed
		ySpd += yAcc;
		
		//attracts towards the exact middle of the screen, can maybe later be changed to 
		//create a BlackHole object in other locations.
		double angle = Math.atan2(SpacePanel.BG_H/2 - yPos, SpacePanel.BG_W/2 - xPos);
		xAcc = 0.15 * Math.cos(angle);     
		yAcc = 0.15 * Math.sin(angle);
		
	}
	
	//a physics/acceleration dependent on state in SpacePanel
	public void wormHolePull() {
		xSpd += xAcc;
		ySpd += yAcc;
		
		//resembles a 3D orbiting around the object
		double angle = Math.atan2(SpacePanel.BG_H/2 - xPos , SpacePanel.BG_W/2  - yPos);

		xAcc = 0.1 * Math.tan(angle);     
		yAcc = 0.1 * Math.tan(angle);
	}
	
	//a physics/acceleration dependent on state in SpacePanel
	public void splitForce() {
		xSpd += xAcc;
		ySpd += yAcc;
		
		//divided forces along the diagonal line from top left to bottom right
		double angle = Math.atan2(-SpacePanel.BG_H/2 + yPos, -SpacePanel.BG_W/2 + xPos);
		
		xAcc = (0.05 * Math.cos(angle));
		yAcc = (0.05 * Math.sin(angle));
	}
	
	
	public void update(Graphics2D g2){
		move();
		drawMe(g2);
	}
	
	//standard movement when no forces applied 
	public void move(){
		xPos += xSpd;		  
		yPos += ySpd;
	}
	
	public abstract void drawMe(Graphics2D g2);
}
