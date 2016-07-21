package particles;

import static primary.SpacePanel.*;

import java.awt.Color;

import primary.Util;

public class ParticleConcreteFactory extends ParticleFactory{

	public Particle createParticle(int num) {
		Particle part = null;
		double xRange = Util.random(-.2, .2);    //x-Velocity range of the particles 
		double yRange = Util.random(0.25, 0.45);   //y-Velocity range of the particles
		
		if (num == 1){
			part = new Particle(Util.random(-50, BG_W-50), Util.random(0, BG_H), xRange, yRange, 1);
		}
		else if (num ==2){
			part = new Particle(Util.random(-50, BG_W-50), Util.random(0, BG_H), xRange, yRange, 2);
		}
		else if (num == 3){
			part = new Particle(Util.random(-50, BG_W-50), 0, xRange, yRange, 1);
		}
		else if (num == 4){
			part = new Particle(Util.random(-50, BG_W-50), 0, xRange, yRange, 2);
		}
		return part;
	}

}
