package org.usfirst.frc.team1389.robot.controls;

import com.team1389.hardware.inputs.hardware.JoystickHardware;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;

/**
 * A basic framework for the robot controls. Like the RobotHardware, one instance of the
 * ControlBoard object is created upon startup, then other methods request the singleton
 * ControlBoard instance.
 * 
 * @author amind
 * @see ControlMap
 */
public class ControlBoard extends ControlMap {
	private static ControlBoard mInstance = new ControlBoard();

	public static ControlBoard getInstance() {
		return mInstance;
	}

	private ControlBoard() {
	}

	private final JoystickHardware driveController = new JoystickHardware(DRIVE_CONTROLLER);
	private final JoystickHardware manipController = new JoystickHardware(MANIP_CONTROLLER);

	// DRIVER CONTROLS
	public PercentIn throttle = driveController.getAxis(ax_THROTTLE_AXIS).applyDeadband(.02).invert();
	public PercentIn wheel = driveController.getAxis(ax_WHEEL_AXIS).applyDeadband(.02).invert();
	public DigitalIn quickTurn = driveController.getButton(btn_QUICK_TURN);
	public DigitalIn test = driveController.getButton(2);

	// MANIPULATOR CONTROLS
	public DigitalIn elevatorDown = manipController.getButton(btn_ELEVATOR_DOWN).latched();
	public DigitalIn elevatorOne = manipController.getButton(btn_ELEVATOR_ONE).latched();
	public DigitalIn elevatorTwo = manipController.getButton(btn_ELEVATOR_TWO).latched();
	public DigitalIn elevatorZero = manipController.getButton(btn_ELEVATOR_ZERO).latched();
}
