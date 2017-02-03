package org.usfirst.frc.team1389.operation;

import java.util.concurrent.CompletableFuture;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.Elevator;
import org.usfirst.frc.team1389.systems.Elevator.Height;
import org.usfirst.frc.team1389.systems.TestSystem;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.util.ButtonEnumMap;
//github.com/team1389/Liftzilla.git
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
		//Subsystem driveSystem = setUpDriveSystem();
		Subsystem testUltra = new TestSystem(robot.ultrasonic, robot.leftA.getPositionInput(), robot.rightA.getPositionInput(), robot.leftA.getSpeedInput(), robot.rightA.getSpeedInput(), robot.leftGroup.getVoltageOutput(), robot.rightGroup.getVoltageOutput(), 30);
		Subsystem elevator = setupComplexElevator();
		manager = new SystemManager(elevator, testUltra);
		manager.init();
		watcher.watch(elevator, testUltra, robot.topSwitch, robot.bottomSwitch);
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.outputToDashboard();

	}

	public void periodic() {
		manager.update();
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
