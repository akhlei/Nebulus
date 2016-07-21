//Class for organization and creation of any text boxes/screens 
//that will be used in the main display classes. Primarily menus,
//pop-up text and information to be provided to the user. 
package primary;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text {
	//specific fonts depending on visible screen
	private Font openingTitleFont;  //font for the main header in opening screen
	private Font menuBodyFont;  //font for the body in opening/ending screen
	private Font endScreenFont;    //font used in the ending screen header
	
	//various timers for text screens
	private double scaleTimer;     //times the gradual inscrease in title scale
	private double startButtonTimer = 186;    //delays the appearance of the start button on first screen
	
	
	public Text() {
		openingTitleFont = new Font("Futura", Font.PLAIN, 48);
		menuBodyFont = new Font("Helvetica", Font.PLAIN, 16);
		endScreenFont = new Font("Futura", Font.BOLD, 36);
		scaleTimer = 1;;
	}
	
	//title screen with opening introduction of application
	public void openingScreen(Graphics2D g2){
		if (scaleTimer < 1.75)    {         //timer used to dictate gradual scaling of title text
			scaleTimer += 0.004;}
		if (startButtonTimer < 0){ 
			startButtonTimer = -1;}
		startButtonTimer --;
		g2.setColor(new Color(40,4,65));   //same color as the main application 
		g2.fill(SpacePanel.getBG());
		AffineTransform trans1 = g2.getTransform();
		g2.translate(330, 100);
		g2.scale(1*scaleTimer, 1*scaleTimer);   //timer is used to grow font over time
		g2.setColor(Color.WHITE);
		g2.setFont(openingTitleFont);
		g2.drawString("N E B U L U S", -84, 40);
		g2.setTransform(trans1);
		g2.setColor(Color.WHITE);
		AffineTransform trans2 = g2.getTransform();
		g2.translate(10, 0);
		//instructions for the user to use the application
		if (scaleTimer > 1.749){        //times the appearance of the body text with title text finishing
			g2.setFont(menuBodyFont);
			g2.drawString("the nebulus is a celestial anomaly with strange capabilities", 230, 240);
			g2.drawString("press the number keys 1, 2, or 3 to test the nebulus", 252, 280);
			g2.drawString("press 4 to revert the nebulus to its resting state", 262,  320);
			g2.drawString("launch asteroids towards the nebulus with the 'a' key", 246, 360); 
			g2.drawString("when you're done experimenting, press the 'e' key", 264, 400);	
		}
		g2.setTransform(trans2);
		
	}
	
	//ending screen with replay button 
	public void endingScreen(Graphics2D g2){
		g2.setColor(new Color(40,4,65));
		g2.fill(SpacePanel.getBG());
		AffineTransform trans = g2.getTransform();
		g2.setColor(Color.WHITE);
		g2.setFont(endScreenFont);
		g2.drawString("Thank You. I Hope You Enjoyed.", 200, 140);
		g2.setTransform(trans);
	}
	
	public double getStartTimer(){
		return startButtonTimer;
	}
}
