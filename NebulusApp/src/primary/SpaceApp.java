/*MAIN APPLICATION:
Angus Lei, 301151047 
N E B U L U S
An interactive fantasy space environment with interactive elements 
which provide an amusing way for the user to pass the time.
A final project for IAT 265 which allows me to practice and attempt
different code which I may be unfamiliar with. */

package primary;

//constructor for the Space App. 
public class SpaceApp extends javax.swing.JFrame{
	public SpaceApp(String title){
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 100);  //where my window frame goes relative to screen
		SpacePanel sp = new SpacePanel(this);
		this.add(sp);
		this.pack();
		this.setVisible(true);
	}
//main loop of code which launches and runs application	
	public static void main(String[] args){
		new SpaceApp ("N E B U L U S");
	}

}

