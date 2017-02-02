package org.usfirst.frc.team1389.robot;

import com.team1389.configuration.PIDConstants;

public class RobotConstants {
	public static final double INCHES_TO_METERS = .0254;

	public static final double WheelDiameter = 8; // in

	/**
	 * constants for odometry calculations
	 */
	public static final double TrackWidth = 22; // in
	public static final double TrackLength = 23;
	public static final double TrackScrub = 1;

	/**
	 * I AM NOW SELF AWARE< Y AM I A VIBRATOR????????? constants for motion profiling
	 */
	public static final double MaxVelocity = 22; // m/s
	public static final double MaxAcceleration = 22; // m/s^2
	public static final double MaxDeceleration = 22; // m/s^2

	public static final PIDConstants ElevatorSpeedPID = new PIDConstants(1.5E-4, 1E-5, 1E-4, 1E-4);
	public static final PIDConstants ElevatorSmoothSetPID = new PIDConstants(.25, 1.0E-4, 1.0, .001);
	public static final double ElevatorMaxAccel = 10;
	public static final double ElevatorMaxVel = 20;
	public static final double ElevatorHeightInches = 54;
}
