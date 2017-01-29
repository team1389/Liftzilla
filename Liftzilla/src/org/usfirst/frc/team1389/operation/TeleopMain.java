package org.usfirst.frc.team1389.operation;

import java.util.concurrent.CompletableFuture;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.Elevator;

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

		
		ButtonEnumMap<Elevator.Height> buttonMap = new ButtonEnumMap<Elevator.Height>(Elevator.Height.Bottom);
		buttonMap.setMappings(buttonMap.new ButtonEnum(controls.armButtonA, Elevator.Height.Bottom), 
				buttonMap.new ButtonEnum(controls.armButtonB, Elevator.Height.Quarter),
						buttonMap.new ButtonEnum(controls.armButtonC, Elevator.Height.Halfway),
								buttonMap.new ButtonEnum(controls.armButtonD, Elevator.Height.ThreeQuarters)/*,
										buttonMap.new ButtonEnum(null, Elevator.Height.Top)*/);
		Subsystem elevator = new Elevator(robot.elevatorPositionIn, robot.elevatorSpeedIn, robot.elevatorVoltage, buttonMap, robot.topSwitchTriggered, robot.bottomSwitchTriggered);

		Subsystem driveSystem = setUpDriveSystem();

		manager = new SystemManager(driveSystem, elevator);
		manager.init();
		watcher.watch(driveSystem);
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.outputToDashboard();
		watcher.watch(elevator);
		
	}

	public void periodic() {
		manager.update();
	}

	public Subsystem setUpDriveSystem() {
		return new CurvatureDriveSystem(robot.drive, controls.throttle, controls.wheel, controls.quickTurn);
	}
}
