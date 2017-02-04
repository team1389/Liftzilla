package org.usfirst.frc.team1389.operation;

import java.util.concurrent.CompletableFuture;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.Elevator;
import org.usfirst.frc.team1389.systems.Elevator.Height;
import org.usfirst.frc.team1389.systems.TestCommandUltra;
import org.usfirst.frc.team1389.systems.TestSystem;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.util.ButtonEnumMap;
//github.com/team1389/Liftzilla.git
import com.team1389.watch.Watcher;
import com.team1389.watch.input.listener.NumberInput;


public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;

	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
	}

	private TestCommandUltra ultraCommand;
	public void init() {
		watcher = new Watcher();
		//controls = ControlBoard.getInstance();
		//Subsystem driveSystem = setUpDriveSystem();
		//Subsystem testUltra = new TestSystem(robot.ultrasonic, robot.leftA.getPositionInput(), robot.rightA.getPositionInput(), robot.leftA.getSpeedInput(), robot.rightA.getSpeedInput(), robot.leftGroup.getVoltageOutput(), robot.rightGroup.getVoltageOutput(), 30);
		//Subsystem elevator = setupComplexElevator();
		//manager = new SystemManager(driveSystem);
		//manager.init();
		watcher.watch(robot.ultrasonicCentimeters.getWatchable("Ultrasonic Position"));
		watcher.watch(new NumberInput("Left A Voltage Set", 0, d -> robot.leftAVoltageOut.set(d)));
		watcher.watch(new NumberInput("Left B Voltage Set", 0, d -> robot.leftBVoltageOut.set(d)));
		watcher.watch(new NumberInput("Right A Voltage Set", 0, d -> robot.rightAVoltageOut.set(d)));
		watcher.watch(new NumberInput("Right B Voltage Set", 0, d -> robot.rightBVoltageOut.set(d)));
		/*watcher.watch(robot.rightAVoltageOut.getWatchable("Last set voltage right A"));
		watcher.watch(robot.leftAVoltageOut.getWatchable("Last set voltage left A"));
		watcher.watch(robot.rightBVoltageOut.getWatchable("Last set voltage right B"));
		watcher.watch(robot.leftBVoltageOut.getWatchable("Last set voltage left B"));*/

		//watcher.watch(new NumberInput("Left speed setter", 0.0, (d) -> robot.leftSpeedOutput.set(d)));
		//watcher.watch(new NumberInput("Right speed setter", 0.0, (d) -> robot.rightSpeedOutput.set(d)));

		

		


		ultraCommand = new TestCommandUltra(robot.leftVoltageOut, robot.rightVoltageOut, robot.ultrasonicCentimeters, 40, robot.drive);
		
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.outputToDashboard();

	}

	public void periodic() {
		//robot.drive.set(0.5, 0.5);
		//manager.update();
		//ultraCommand.exec();
		

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
