
package org.usfirst.frc.team1389.robot;

import org.usfirst.frc.team1389.operation.TeleopMain;
import org.usfirst.frc.team1389.systems.Elevator;
import org.usfirst.frc.team1389.watchers.DashboardInput;

import com.team1389.auto.AutoModeExecuter;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotSoftware robot;
	TeleopMain teleOperator;
	AutoModeExecuter autoModeExecuter;
	Elevator elevator;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robot = RobotSoftware.getInstance();
		teleOperator = new TeleopMain(robot);
		autoModeExecuter = new AutoModeExecuter();
		elevator = new Elevator(robot.elevatorPositionIn, robot.elevatorSpeedIn, robot.elevatorVoltage, null, robot.topSwitchTriggered, robot.bottomSwitchTriggered);
	
	}

	@Override
	public void autonomousInit() {
		autoModeExecuter.stop();
		autoModeExecuter.setAutoMode(DashboardInput.getInstance().getSelectedAutonMode());
		autoModeExecuter.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void teleopInit() {
		elevator.init();
		autoModeExecuter.stop();
		teleOperator.init();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		teleOperator.periodic();
		elevator.update();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

}
