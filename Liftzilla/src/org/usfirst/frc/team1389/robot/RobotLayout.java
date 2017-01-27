package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.hardware.NavXHardware;
import com.team1389.hardware.outputs.hardware.CANTalonGroup;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;

/**
 * contains a list of declared hardware objects for this robot. Separated from
 * {@link RobotHardware} to make it easier to see what hardware is connected to
 * the robot.
 * 
 * @author amind
 *
 */
public class RobotLayout extends RobotMap {
	public Registry registry;

	public CANTalonHardware leftA;
	public CANTalonHardware rightA;
	public CANTalonHardware leftB;
	public CANTalonHardware rightB;
	
	public CANTalonGroup leftGroup;
	public CANTalonGroup rightGroup;
	
	public VictorHardware elevatorA;
	public VictorHardware elevatorB;

	public NavXHardware navX;
	

}
