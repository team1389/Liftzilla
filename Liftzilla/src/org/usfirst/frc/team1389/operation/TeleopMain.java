package org.usfirst.frc.team1389.operation;

import java.util.concurrent.CompletableFuture;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.ComplexElevator;
import org.usfirst.frc.team1389.systems.Elevator;
import org.usfirst.frc.team1389.systems.VoltageElevator;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
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
				buttonMap.new ButtonEnum(controls.armButtonD,
						Elevator.Height.ThreeQuarters)/*
														 * , buttonMap.new ButtonEnum(null,
														 * Elevator.Height.Top)
														 */);
		// Subsystem elevator = new Elevator(robot.elevatorPositionIn, robot.elevatorSpeedIn,
		// robot.elevatorVoltage, buttonMap, robot.topSwitchTriggered, robot.bottomSwitchTriggered);

		Subsystem driveSystem = setUpDriveSystem();
		Subsystem elevator = setupComplexElevator();
		manager = new SystemManager(elevator, driveSystem);
		manager.init();
		watcher.watch(elevator, driveSystem, robot.topSwitch, robot.bottomSwitch);
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
		watcher.watch(topSwitchTriggered.getWatchable("top"));
		return new ComplexElevator(robot.elevatorVoltage, robot.elevatorSpeedIn, robot.elevatorPositionIn,
				topSwitchTriggered, bottomSwitchTriggered);
	}

	private Subsystem setupVoltageElevator() {
		PercentOut elevator = robot.elevatorVoltage;
		PercentIn joy = controls.liftAxis;
		RangeIn<Position> pos = robot.elevatorPositionIn;
		RangeIn<Speed> speed = robot.elevatorSpeedIn;
		return new VoltageElevator(elevator, joy, speed, pos);
	}
}
