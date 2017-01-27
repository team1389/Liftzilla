package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.DriveOut;

public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();
	public final DriveOut<Percent> drive = new DriveOut<Percent>(rightGroup.getVoltageOutput().scale(0.5), leftGroup.getVoltageOutput().scale(0.5));
	public final RangeIn<Position> elevatorPositionIn = leftB.getPositionInput();
	public final RangeIn<Speed> elevatorSpeedIn = leftB.getSpeedInput();
	public final PercentOut elevatorVoltage = elevatorA.getVoltageOutput().addFollowers(elevatorB.getVoltageOutput());
	public final DigitalIn topSwitchTriggered = topSwitch.getSwitchInput();
	public final DigitalIn bottomSwitchTriggered = bottomSwitch.getSwitchInput();
	//	public AngleIn<Position> angle;
	//public RobotStateEstimator state;

	/*public RobotStateEstimator setupRobotStateEstimator() {
		RangeIn<Position> left = leftA.getPositionInput().setRange(0, 1440).mapToRange(0,
				Math.PI * RobotConstants.WheelDiameter);
		RangeIn<Position> right = rightA.getPositionInput().setRange(0, 1440).mapToRange(0,
				Math.PI * RobotConstants.WheelDiameter);
		RangeIn<Speed> leftS = leftA.getSpeedInput().setRange(0, 1440).mapToRange(0, Math.PI * 4);
		RangeIn<Speed> rightS = rightA.getSpeedInput().setRange(0, 1440).mapToRange(0, Math.PI * 4);
		AngleIn<Position> gyro = angle;

		return new RobotStateEstimator(new RobotState(), left, right, leftS, rightS, gyro, RobotConstants.TrackWidth,
				RobotConstants.TrackLength, RobotConstants.TrackScrub);
	}
*/
	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
		//angle = navX.getYawInput();
		//state = setupRobotStateEstimator();
	}

}
