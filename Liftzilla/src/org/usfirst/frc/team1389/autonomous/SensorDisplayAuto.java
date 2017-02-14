package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class SensorDisplayAuto extends AutoModeBase {
	RobotSoftware robot;

	public SensorDisplayAuto(RobotSoftware robot) {
		this.robot = robot;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(robot.leftA.getPositionInput().getWatchable("leftPosInches"));
	}

	@Override
	public String getName() {
		return "testAuto";
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
