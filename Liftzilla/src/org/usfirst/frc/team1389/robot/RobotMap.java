package org.usfirst.frc.team1389.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * <b>Conventions</b>: <br>
 * For I/O ports, the naming convention is <em>type_ALL_CAPS_IDENTIFIER</em>.
 * for example, a talon port might be named can_LEFT_MOTOR_A. Possible port
 * types and identifiers are CAN (can), Analog (anlg), PWM (pwm), USB (usb), PCM
 * (pcm), DIO (dio), etc
 * <p>
 * Inputs and Outputs may be inverted. The inversions in this map should only
 * relate to the physical configuration of the robot. A positive value should
 * cause the output to move in the most logical direction (I.e, the drive motors
 * should move forward with positive voltage values) <br>
 * the convention for inversion constants is
 * <em>inv_ASSOCIATED_IO_IDENTIFIER</em> for outputs and
 * <em>sinv_ASSOCIATED_IO_IDENTIFIER</em> for inputs.
 */
public class RobotMap {
	//Outputs
	
	//Drivetrain
	protected static final int can_LEFT_MOTOR_A = 9; protected static final boolean inv_LEFT_MOTOR_A = true;
	protected static final int can_LEFT_MOTOR_B = 1; protected static final boolean inv_LEFT_MOTOR_B = true;
	protected static final int can_RIGHT_MOTOR_A = 5; protected static final boolean inv_RIGHT_MOTOR_A = false;
	protected static final int can_RIGHT_MOTOR_B = 7; protected static final boolean inv_RIGHT_MOTOR_B = false;
	
	protected static final int victor_LEFT = 9;
	protected static final int victor_RIGHT = 1;

	
	
	
	
	
	protected static final boolean sinv_LEFT_ENCODER = true;
	protected static final boolean sinv_RIGHT_ENCODER = false;
	protected static final boolean sinv_ELEVATOR_ENCODER = true;
	protected static final boolean sinv_FLYWHEEL_ENCODER = false;	

}

