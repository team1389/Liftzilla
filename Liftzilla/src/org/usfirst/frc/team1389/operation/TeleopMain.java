package org.usfirst.frc.team1389.operation;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.Elevator;
import org.usfirst.frc.team1389.systems.Elevator.Height;
import org.usfirst.frc.team1389.systems.FailureMonitor;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.util.ButtonEnumMap;
import com.team1389.watch.Watcher;

public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;

	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
	}

	public void init() {
		watcher = new Watcher();
		controls = ControlBoard.getInstance();
		Subsystem driveSystem = setUpDriveSystem();
		Subsystem elevator = setupComplexElevator();
		Subsystem failureMonitor = setUpFailureMonitorSystem();
		manager = new SystemManager(elevator, driveSystem, failureMonitor);
		manager.init();
		watcher.watch(elevator, driveSystem, robot.topSwitch, robot.bottomSwitch, robot.robotAngle.getWatchable("gyro-position"));
		watcher.outputToDashboard();

	}

	public void periodic() {
		manager.update();
		Watcher.update();
	}
	private Subsystem setUpFailureMonitorSystem(){
		return new FailureMonitor(robot.pdp, controls.throttle);
	}
	public Subsystem setUpDriveSystem() {
		return new CurvatureDriveSystem(robot.drive, controls.throttle, controls.wheel, controls.quickTurn);
	}

	private Subsystem setupComplexElevator() {
		DigitalIn topSwitchTriggered = robot.topSwitch.getSwitchInput().invert();
		DigitalIn bottomSwitchTriggered = robot.bottomSwitch.getSwitchInput();
		ButtonEnumMap<Height> buttonMap = new ButtonEnumMap<>(Height.BOTTOM);
		buttonMap.setMappings(buttonMap.new ButtonEnum(controls.elevatorDown, Height.BOTTOM),
				buttonMap.new ButtonEnum(controls.elevatorOne, Height.ONE_TOTE),
				buttonMap.new ButtonEnum(controls.elevatorTwo, Height.TWO_TOTE));
		return new Elevator(robot.elevatorVoltage, robot.elevatorSpeedIn, robot.elevatorPositionIn, topSwitchTriggered,
				bottomSwitchTriggered, controls.elevatorZero, buttonMap);
	}

}
