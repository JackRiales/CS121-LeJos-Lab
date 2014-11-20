
// Motorized robot class by Jack Riales

package fRobotProject;
import lejos.nxt.*;

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
	public float distanceMoved	= 0;
	
	// Power is the strength of the signal sent to the motors
	// clamped to a 0-100 value (to coincide with controlMotors)
	public int power			= 80;
	
	// Modes. Immutable.
	// Determines which mode to active when controlling motors.
	final int forward			= 1;
	final int stop				= 3;
	final int flt				= 4;
	
	// Collection of motor ports possible to be used by a given robot.
	// Null signifies that the port is not in use.
	public final BasicMotorPort a	= MotorPort.A;
	public final BasicMotorPort b	= MotorPort.B;
	public final BasicMotorPort c	= MotorPort.C;
	
	// Virtual method run allows for final robot to
	// determine how it runs in its thread.
	public abstract void run();
	
	Robot() 
	{
		; // Default
	}
	
	public String toString()
	{
		return
			"Active:\n" + active +
			"\nLocal Direction:\n" + localDirection +
			"\nDistance Travelled:\n" + distanceMoved + 
			"\nCurrent Speed:\n" + power;
	}
	
	public void drawToLCD(String toDraw)
	// Draws a string at the origin vector
	{
		LCD.clear();
		LCD.drawString(toDraw, 0, 0);
	}
	
	public void drawToLCD(String toDraw, int x, int y)
	// Draws a string to the LCD at a given vector
	{
		LCD.clear();
		LCD.drawString(toDraw, x, y);
	}
	
	public void signalMove(BasicMotorPort a, BasicMotorPort b)
	// Signals two mobility motors to rotate at the set power. Will not stop until
	// a manual stop is called on the motors.
	{
		a.controlMotor(power, forward);
		b.controlMotor(power, forward);
	}
	
	public void signalMove(BasicMotorPort a, BasicMotorPort b, float time)
	// Signals move for a given time
	{
		boolean movement = true;
		long lStartTime = System.currentTimeMillis();
		signalMove(a, b);
		while(movement) {
			long lEndTime = System.currentTimeMillis();
			if(time <= lEndTime - lStartTime) {
				movement = !movement;
			}
		}
		signal(a, 0, stop);
		signal(b, 0, stop);
		distanceMoved = power * (time / 1000);
	}
	
	public void signalRotate(BasicMotorPort driver)
	// Signals one motor to move, created rotation
	{
		signal(driver, power, forward);
	}
	
	public static void signal(BasicMotorPort motor, int power, int mode)
	// Sends a low level signal at the given power and mode
	{
		motor.controlMotor(power, mode);
	}
	
	public void killMotors()
	// Signals all motors to stop immediately
	{
		signal(a, 0, stop);
		signal(b, 0, stop);
		signal(c, 0, stop);
	}
}