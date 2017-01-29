package org.usfirst.frc.team1389.operation;

import java.util.concurrent.CompletableFuture;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;

import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.watch.Watcher;

public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;

	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
		watcher = new Watcher();
	}

	public void init() {
		controls = ControlBoard.getInstance();
		Subsystem driveSystem = setUpDriveSystem();

		manager = new SystemManager(driveSystem);
		manager.init();
		watcher.watch(driveSystem);
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.outputToDashboard();
	}

	public void periodic() {
		manager.update();
	}

	public Subsystem setUpDriveSystem() {
		return new CurvatureDriveSystem(robot.drive, controls.throttle, controls.wheel, controls.quickTurn);
	}
}
