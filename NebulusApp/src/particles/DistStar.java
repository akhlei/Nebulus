package particles;
import java.awt.*;
import java.awt.geom.*;

import primary.Util;

public class DistStar {
	//fields for position 
	protected double xPos, yPos;
	protected double dia;
	protected double scale;
	
	protected Color coreColor; //inner high saturation and brightness color of the particle
	protected Color glowColor; //outer low brightness faint glow color
	
	protected Ellipse2D.Double core;  
	protected Ellipse2D.Double glow; 
	
	public DistStar(double x, double y){
		xPos = x;
		yPos = y;
		dia = 1.5;
		scale = Util.random(1, 1.5);
		
		coreColor = new Color(30, 255, 255);
		glowColor = new Color(255, 255, 255);
		
		core = new Ellipse2D.Double();
		glow = new Ellipse2D.Double();
	}
	
	public void drawMe(Graphics2D g2){
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		core.setFrame(-dia+75, -dia+75, dia/2, dia/2);
		glow.setFrame(-dia, -dia, dia, dia);
		//drawing component
		g2.setColor(glowColor);
		g2.fill(glow);
		g2.setColor(coreColor);
		g2.fill(core);
		g2.setTransform(transform);
	}

}
