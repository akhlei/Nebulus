/*SPACE PANEL
Main panel upon which graphical elements of the application are updated
and drawn. All higher scope interactive and behavioral management
code will appear here. 
*/

package primary;
import particles.*;
import primary.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

import entities.*;
import sound.*;
import java.util.ArrayList;


public class SpacePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	//values for the panel size, can be referenced by other classes
	public static final int BG_W = 900;
	public static final int BG_H = 600;
	
	private JFrame frame; 
	
	//background space object/color
	private static Rectangle2D.Double bg;
	private Color bgColor = new Color(25,2,35);   //currently a dark purple
	
	//individual Arrays for different particles as well as a general
	//array list for multiple object management
	private ArrayList<Particle> bgParticles = new ArrayList<Particle>();
	private ArrayList<FarStar> bgStars = new ArrayList<FarStar>();
	private ArrayList<DistStar> distStars = new ArrayList<DistStar>(); 
	private ArrayList<Nebula> nebula = new ArrayList<Nebula>();
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	
	//an arraylist for the image based planets which will be called, organized by their superclass type 
	private ArrayList<CelestialBeing> planets = new ArrayList<CelestialBeing>();
	
	//factory design patterns for organization and improved encapsulation	
	private ParticleFactory partFactory = new ParticleConcreteFactory(); 
	
	//start screen variables
	private JButton startButton;
	
	//replay variables 
	private JButton replayButton;
	private boolean replay = false;
	
	//back-end operational variables for the application 
	private Timer timer; 
	private int state = 1;
	private Text txt;
	
	
	//values utilized by the application to adjust number of objects instantiated of various classes
	private int particleDensity  = 650;     //number of particle class objects on screen
	private int starDensity = (int)Util.random(20, 30);    //random bounded number of stars 
	private int distantDensity = (int)Util.random(75, 100);    //random number of further stars
	
	private double nebulaAngle = 0;     //tracks individual nebula angle 
	//CHECK LECTURE NOTES TO SEE IF THIS NEBULA ANGLE CAN BE INCORPORATED IN CLASS -- REDUCE LAG?
	private int nebulaDensity = 75;   //amount of nebula objects used to make the nebula twister
	
	//variables for states related to acceleration altering commands
	private boolean blackHole;
	private boolean wormHole;
	private boolean splitForce;
	
	
	//constructor for the canvas/panel for the space application
	public SpacePanel(JFrame frame){
		this.frame = frame;
		this.setBackground(Color.gray);
		bg = new Rectangle2D.Double();
		this.setPreferredSize(new Dimension(BG_W, BG_H));
		bg.setFrame(0, 0, BG_W, BG_H);
		sound.Sound.play("assets/spacetrack.wav");
		
		//instantiate the particles which will be on screen at startup
		for (int i=0; i< particleDensity ; i++){
			//double scale = Util.random(0.75, 1); currently keeping the particles uniform
			int r = (int)(Util.random(0, 2));
			if (r < 1) bgParticles.add(partFactory.createParticle(1));
			else bgParticles.add(partFactory.createParticle(2));
		}
		
		for (int i=0; i<distantDensity; i++){
			distStars.add(new DistStar(Util.random(5, BG_W-5), Util.random(5, BG_H-5)));
		}
		
		//instantiate the stars in the background
		for (int i=0; i< starDensity; i++){
			bgStars.add(new FarStar(Util.random(20, BG_W-20), Util.random(20, BG_H-20)));
		}
		
		//instantiate unique celestial entities visible at startup
		planets.add(new Moon(490, 20));
		planets.add(new GasGiant(80, 80));
		planets.add(new RockPlanet(540,360));
		
		//instantiate the buttons/text for environment UI 
		txt = new Text();   //necessary in game introduction text ges  here, fade after time(?)
		//replay button
		replayButton = new JButton("Replay");
		this.add(replayButton, BorderLayout.AFTER_LINE_ENDS);
		replayButton.setVisible(false);
		replayButton.addActionListener(this);
		//start button
		startButton = new JButton("Begin");
		this.add(startButton, BorderLayout.SOUTH);
		startButton.setVisible(false);
		startButton.addActionListener(this);
		
		//necessary back-end interaction/animation related variables
		addMouseListener(this);
		timer = new Timer(30, this);
		timer.start();
		MyKeyListener mkl = new MyKeyListener();
		this.addKeyListener(mkl);
		this.setFocusable(true);
		
		//initial physics states at the start of the application instantiated as false
		blackHole = false;
		wormHole = false;
		splitForce = false;
	}
	
	
	
	//key controls 
	public class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
	
	// 'A' key instantiates asteroid objects from off screen
			if (e.getKeyCode() == KeyEvent.VK_A){
				double astaSide = Util.random(0, 2);
				if (astaSide > 0 && astaSide <= 1){
					asteroids.add(new Asteroid(Util.random(-50, 0), Util.random(50, BG_H-50), Util.random(2.5, 6.5), Util.random(-2, 2), Util.random(0.75, 1.25)));
				} else if (astaSide > 1 && astaSide <= 2){
					asteroids.add(new Asteroid (Util.random(BG_W, BG_W+50), Util.random(50, BG_H-50), Util.random(-6.5, -2.5), Util.random(-2, 2), Util.random(0.75, 1.25)));
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_1){
				wormHole = true;
				blackHole = false;
				splitForce = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_2){
				blackHole = true;
				splitForce = false;
				wormHole = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_3){
				splitForce = true;
				blackHole = false;
				wormHole = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_4){
				splitForce = false;
				wormHole = false;
				blackHole = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_E){
				state = 2;
			}
		}
	}
	
	/*
	 * Contains call for all of the classes to be displayed in the main 
	graphical paint loop. This includes the calling/removal of further objects 
	for the following classes: Nebula, Farstar, and Particle
	since the elements are mostly decorative interactive, they are grouped in this
	specific section separate from the purely objects 
	
	*/
	public void mainDisplay(Graphics2D g2){
		//loop for referencing of the background stars, called for first
		//because they're the "further" objects 
		for (int i = 0; i < bgStars.size(); i++){
			FarStar star = bgStars.get(i);
			star.drawMe(g2);
		}
		for (int i = 0; i< distStars.size(); i++){
			DistStar twinkle = distStars.get(i);
			twinkle.drawMe(g2);
		}
		
		//call instances of planets (CelestialBeing class objects)
		for (int i = 0; i < planets.size(); i++){
			CelestialBeing cb = planets.get(i);
			cb.drawMe(g2);
		}
	
		
		//handles the removal of background particles once they've exceeded the boundary
		//of the main application frame as well as calling their physics methods
		for (int i = 0; i < bgParticles.size(); i++) {
			Particle part = bgParticles.get(i);	
			//boundary detection and deletion of particles
			if (part.getYPos() > SpacePanel.BG_H ){
				bgParticles.remove(part);
			}
			if (part.getXPos() > SpacePanel.BG_W ){
				bgParticles.remove(part);
			}
			if (part.getXPos() < 0){
				bgParticles.remove(part);
			}
			part.update(g2); 
			if (blackHole == true) part.blackHolePull();
			if (wormHole == true) part.wormHolePull();
			if (splitForce == true) part.splitForce();
		}
		
		//loop for instantiation of further background particles once the initially
		//declared particles are removed from the particle list
		if (bgParticles.size() < particleDensity ){
			int r = (int)(Util.random(0, 2));
			if (r < 1) bgParticles.add(partFactory.createParticle(3));
			else bgParticles.add(partFactory.createParticle(4));
		}
		
		//runs the update method (which includes graphical display and movement behaviour)
		// of the Nebula class, as well as handling the removal of Nebula objects 
		for (int i = 0; i < nebula.size(); i++){
			Nebula nebulaPart = nebula.get(i);
			//nebula particle destruction management 
			if (nebulaPart.getLife() < 0) nebula.remove(nebulaPart);
			if (nebulaPart.getX() > BG_W || nebulaPart.getX() < 0) nebula.remove(nebulaPart);
			if (nebulaPart.getY() > BG_H || nebulaPart.getY() < 0) nebula.remove(nebulaPart);
			
			//display and physics of nebula particles
			nebulaPart.update(g2);
			if (blackHole == true) {
				nebulaPart.blackHolePull();
				nebulaPart.setLife(Util.random(80, 100));
			} if (wormHole == true){
				nebulaPart.wormHolePull();
				nebulaPart.setLife(Util.random(80, 100));

			} if (splitForce == true){
				nebulaPart.splitForce();
				nebulaPart.setLife(Util.random(80, 100));
				
			}
		}

		//nebula factory could potentially go here instead of this
		// calls for nebula particle objects, and alter the angle of each nebula object that is called 
		if (nebulaAngle > 360) nebulaAngle = 0;
		nebulaAngle += 6.5;
		if (nebula.size() < nebulaDensity) nebula.add (new Nebula(Util.random(BG_W/2-2,BG_W/2+2), Util.random(BG_H/2-2,BG_H/2+2), nebulaAngle, Util.random(0.25, 0.3)));
		
	
		//handles the destruction of asteroid objects once they're out of bounds by calling
		//the destroyAsteroids method 
		for (int i=0; i < asteroids.size(); i++){
			Asteroid asta = asteroids.get(i);
			if (asta.getXPos() + (2*(-asta.getW()*asta.getScale())) > BG_W) 
				destroyAsteroid(asta);
			if (asta.getXPos() - (2*(-asta.getW()*asta.getScale())) < 0)
				destroyAsteroid(asta);
			if (asta.getYPos() + (2*(-asta.getH()*asta.getScale())) > BG_H)
				destroyAsteroid(asta);
			if(asta.getYPos() - (2*(-asta.getH()*asta.getScale())) < 0)
				destroyAsteroid(asta);
			
			//handles the detectCollision and any subsequent actions which accompany the 
			//destruction of an Asteroid object
			for (int j = i + 1; j < asteroids.size(); j++) {
				Asteroid otheroid = asteroids.get(j);
			//if the asteroids collide, destory them and play a sound effect
				if (asta.detectCollision(otheroid)) {
					destroyAsteroid(asta);
					destroyAsteroid(otheroid);
					sound.Sound.play("assets/boom.wav"); 
				}

			}
			//physics applied to the asteroid objects depending on the users interaction
			asta.update(g2);
			if (blackHole == true)	{
				asta.blackHolePull();
			} 
			if (wormHole == true){
				asta.wormHolePull();
			}
			if (splitForce == true){
				asta.splitForce();
			}
		}
	}
	
	//primary paint component class where instantiated objects from other methods are called.
	//state-based game flow is directed here as well
	public void paintComponent(Graphics g){ 
	 	super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//game flow state distribution 
		if (state == 0){
			startButton.setVisible(false);
			if (txt.getStartTimer() < 0) startButton.setVisible(true); 
			txt.openingScreen(g2);
			replayButton.setVisible(false);
		}
		else if (state == 1){
			super.paintComponent(g);
			g2.setColor(bgColor);
			g2.fill(bg);
			mainDisplay(g2);
			replayButton.setVisible(false);
			startButton.setVisible(false);
		}
		else if (state == 2){
			txt.endingScreen(g2);
			timer.stop();
			startButton.setVisible(false);
			replayButton.setVisible(true);
			replay = true;
		}
		
	}
	
	//overridden action listener
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//game flow direction determined from button press action events 
		if (e.getActionCommand()=="Replay"){
			state = 0;
		}
		if (e.getActionCommand()=="Begin"){
			state = 1;
		}
		
		timer.start();
		
			//any key command if statements and the corresponding response should be implemented here
			/* example from BugApp :
			 * if (right)
					avtBug.move(4, avtBug.getYSpeed());
				if (up)
					avtBug.move(avtBug.getXSpeed(), -4);
				
			//this environment could have buttons to add asteroids/nebula
			 * alternatively the key press command can have a color changing feature for certain
			 * particle classes
			 */  
		
		repaint();
		
		if (replay) {
			frame.dispose();
			frame = new SpaceApp("N E B U L U S");
			replay = false;
		}
	}
	
	//method for cleaner removal of Asteroid class objects from asteroid list 
	private void destroyAsteroid(Asteroid a) {
		asteroids.remove(a); 
	}
	
	//returns a rectangle for the background 
	public static Rectangle2D.Double getBG(){
		return bg;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		
		//clickable actions for asteroid, destroys asteroid and runs the "destroyed" method
		for (int i=0; i < asteroids.size(); i++){
			Asteroid roid = asteroids.get(i);
			
			if (roid.checkMouseBound(x, y)){
				destroyAsteroid(roid);
				sound.Sound.play("assets/boom.wav");   //currently too loud, need to Audacity this later to soften the amplitude of the sound 
			}
		}
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
