package fRobotProject;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

public class CTRobot extends Robot
{
	public static NXTRegulatedMotor B = Motor.B;
	public static NXTRegulatedMotor C = Motor.C;
	public ColorSensor sensor;
	public int colorToTrack;
	public int attempts = 0;
	public int maxAttempts = 10;
	
	CTRobot()
	{
		;
	}
	
	CTRobot(lejos.nxt.SensorPort port, int colorToTrack) 
	{
		sensor = new ColorSensor(port, SensorConstants.TYPE_LIGHT_ACTIVE);
		this.colorToTrack = colorToTrack;
	}
	
	public boolean checkColor()
	{
		sensor.setFloodlight(colorToTrack);
		if(sensor.getColorID() == colorToTrack) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		CTRobot ctr = new CTRobot(SensorPort.S1, Color.RED);
		ctr.speed = 500;
		while(ctr.active) {
			LCD.clear();
			LCD.drawString(ctr.toString(), 0, 0);
			
			if(ctr.checkColor())
				ctr.signalMove(B, C, 1000);
			
			else {
				Sound.beep();
				ctr.signalRotate(B, C, 90);
				ctr.attempts++;
			}
			
			if(ctr.attempts == ctr.maxAttempts) {
				LCD.clear();
				LCD.drawString("Fuck it", 100, 100);
				ctr.active = false;
			}
		}
	}
}