// Pathfinding robot class by Jack Riales

package fRobotProject;
import lejos.nxt.*;

public class PathfindingRobot extends Robot
{
	// Light sensor used to detect the path line.
	// Change sensor port as necessary.
	LightSensor light 			= new LightSensor(SensorPort.S3);
	
	// Light detection threshold. Testing needed to find proper value.
	public final int threshold 	= 45;

	public void run() {		
		// Use the light sensor as a reflection sensor
		light.setFloodlight(true);
				
		// Show light percent until LEFT is pressed
		LCD.drawString("Press LEFT", 0, 2);
		LCD.drawString("to start", 0, 3);
		while (! Button.LEFT.isDown()){
			LCD.drawInt(light.readValue(), 3, 9, 0);
		}
		
		// Follow line until ESCAPE is pressed
		LCD.drawString("Press ESCAPE", 0, 2);
		LCD.drawString("to stop ", 0, 3);
		while (active){
			if(Button.ESCAPE.isDown()) active = false;
			
			if (detected()){
				// On white, turn right
				signal(b,0,stop);
				signal(c,power, forward);
			}
			else{
				// On black, turn left
				signal(b,power, forward);
				signal(c,0,stop);
			}
			try {
				Thread.sleep(5);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		// Stop car gently with free wheel drive
		signal(b,0,flt);
		signal(c,0,flt);
		LCD.clear();
		LCD.drawString("Program stopped", 0, 0);
		try {
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean detected()
	// Determines if the detected light is greater than the threshold
	{
		return light.readValue() > threshold;
	}
}