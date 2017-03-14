package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.system.drive.DriveOut;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class SimpleAuto extends AutoModeBase {
	RobotSoftware robot;

	public SimpleAuto(RobotSoftware robot) {
		this.robot = robot;
		DriveOut<Percent> copy = robot.drive.copy().invert();
		turnController = TurnAngleCommand.createTurnController(copy);
	}

	RangeOut<Percent> turnController;

	@Override
	protected void routine() throws AutoModeEndedException {
		robot.gyro.reset();
		runCommand(new TurnAngleCommand<>(90, 1, robot.gyro.getAngleInput(),
						turnController, new PIDConstants(0.01, .0001, .08)));
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {

		return stem.put(turnController.getWatchable("lastSet"));
	}

	@Override
	public void done() {
		super.done();
		turnController.set(0.0);
	}

	@Override
	public String getIdentifier() {
		return "Simple auto";
	}

}
