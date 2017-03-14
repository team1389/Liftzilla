package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.CommandScheduler;
import com.team1389.command_framework.CommandUtil;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
import com.team1389.trajectory.PathFollowingSystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class AutoDropOffGear extends AutoModeBase {
	CommandScheduler scheduler;
	PathFollowingSystem cont;
	Trajectory traj;
	RobotSoftware robot;

	public AutoDropOffGear(RobotSoftware robot) {
		this.robot = robot;
		PathFollowingSystem.Constants constants = new PathFollowingSystem.Constants(200, 20, 12, .001, .004, 0, 0.65,
				.6);
		cont = new PathFollowingSystem(robot.drive,
				robot.leftA.getPositionInput().<PositionEncoderIn>setTicksPerRotation(1440).getInches(),
				robot.rightA.getPositionInput().getInches(),
				robot.gyro.getAngleInput(), constants);

	}

	@Override
	protected void routine() throws AutoModeEndedException {

		Waypoint[] points = new Waypoint[] { new Waypoint(0, 30, 0), new Waypoint(-101, 56, Pathfinder.d2r(300)) };
		Waypoint[] points2 = new Waypoint[] { new Waypoint(50, 50, 0), new Waypoint(-20, -20, 0) };
		runCommand(CommandUtil.combineSequential(cont.new PathFollowCommand(points, false, 180),
				CommandUtil.createCommand(robot.gyro::reset), cont.new PathFollowCommand(points2, false, 180)));
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem;// .put(cont.getSubWatchables(CompositeWatchable.makeStem()));
	}

	@Override
	public String getIdentifier() {
		return null;
	}

}
