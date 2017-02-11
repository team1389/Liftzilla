package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.command_framework.CommandScheduler;
import com.team1389.command_framework.CommandUtil;
import com.team1389.hardware.value_types.Position;
import com.team1389.trajectory.PathFollowingSystem;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class AutoDropOffGear {
	CommandScheduler scheduler;
	PathFollowingSystem cont;
	Trajectory traj;
	RobotSoftware robot;

	public AutoDropOffGear(RobotSoftware robot) {
		this.robot = robot;
		scheduler = new CommandScheduler();
		initialize();
	}

	protected void initialize() {

		Waypoint[] points = new Waypoint[] { new Waypoint(0, 30, 0), new Waypoint(-101, 56, Pathfinder.d2r(300)) };
		Waypoint[] points2 = new Waypoint[] { new Waypoint(50, 50, 0), new Waypoint(-20, -20, 0) };

		PathFollowingSystem.Constants constants = new PathFollowingSystem.Constants(200, 20, 240, .17, .004, 0, 0.65,
				.6);
		cont = new PathFollowingSystem(robot.drive, robot.leftA.getPositionInput(), robot.rightA.getPositionInput(),
				robot.gyro.getAngleInput(), constants);

		scheduler.schedule(CommandUtil.combineSequential(cont.new PathFollowCommand(points, false, 180),
				CommandUtil.createCommand(() -> robot.gyro.reset()), cont.new PathFollowCommand(points2, false, 180)));

	}

	protected void update() {
		scheduler.update();

	}
}
