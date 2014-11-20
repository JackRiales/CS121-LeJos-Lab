// Jousting robot class by Jack Riales

package fRobotProject;

import lejos.nxt.*;

public class JoustingRobot extends Thread
{
	// The host robot that implements motors
	public Robot host;
	
	// Touch sensor used for the "lance."
	// Change sensor port as necessary.
	public TouchSensor lance = new TouchSensor(SensorPort.S2);
	
	// How many "points" does the robot have
	public int points = 0;
	
	// How many points will the robot receive for hitting something?
	public int increment = 5;
	
	// How many real hits has the robot made?
	public int hits = 0;
	
	public JoustingRobot(Robot host)
	{
		this.host = host;
	}
	
	public JoustingRobot(Robot host, SensorPort lance)
	{
		this.host = host;
		this.lance = new TouchSensor(lance);
	}
	
	@Override
	public void run() 
	{
		while(host.active) {
			host.drawToLCD("Points: " + points + "\nHits: " + hits);
			if(lance.isPressed()) {
				Sound.beep();
				host.drawToLCD("Item it!");
				points += increment;
				hits++;
				
				// Go backwards
				host.power *= -1;
				host.signalMove(host.b, host.c, 5000);
				
				try {
					// Sleep for five seconds
					Thread.sleep(5000);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				host.power *= -1;
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		PathfindingRobot pfr = new PathfindingRobot();
		JoustingRobot jr = new JoustingRobot(pfr);
		jr.start();
		pfr.run();
	}
}