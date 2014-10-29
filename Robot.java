package fRobotProject;
import lejos.nxt.NXTRegulatedMotor;

public abstract class Robot
{
	// Determines if the robot is currently active and still performing actions
	public boolean active		= true;
		
	// Determines the current direction the robot is pointing
	// When the robot is instantiated, the direction its facing is the origin
	// If it turns 90 degrees, localDirection is then "90"
	public int localDirection	= 0;
	
	// Keeps track of the amount of distance the robot has moved.
	// Used merely for LCD display
	public int distanceMoved	= 0;
	
	// Speed signal sent to the robot when moving
	public float speed			= 0;
	
	Robot() 
	{
		; // Default
	}
	
	Robot(float speed) 
	{
		this.speed = speed;
	}
	
	public String toString()
	{
		return
			"Active:\n" + active +
			"\nLocal Direction:\n" + localDirection +
			"\nDistance Travelled:\n" + distanceMoved + 
			"\nCurrent Speed:\n" + speed;
	}
	
	public void signalMove(NXTRegulatedMotor a, NXTRegulatedMotor b, float time)
	{
		boolean movement = true;
		long lStartTime = System.currentTimeMillis();
		while(movement) {
			a.setSpeed(speed);
			b.setSpeed(speed);
			a.forward();
			b.forward();
			long lEndTime = System.currentTimeMillis();
			if(time <= lEndTime - lStartTime) {
				movement = false;
			}
		}
		a.stop();
		b.stop();
		distanceMoved+=speed * time / 1000;
	}
	
	public void signalRotate(NXTRegulatedMotor a, NXTRegulatedMotor b, int amount)
	{
		a.rotate(amount);
		localDirection += amount;
	}
}