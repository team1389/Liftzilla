package org.usfirst.frc.team1389.robot;

import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.DriveOut;

public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();
	public final DriveOut<Percent> drive = new DriveOut<Percent>(rightGroup.getVoltageOutput(),
			leftGroup.getVoltageOutput());
	public final RangeIn<Position> elevatorPositionIn = rightB.getPositionInput();
	public final RangeIn<Speed> elevatorSpeedIn = rightB.getSpeedInput();
	public final PercentOut elevatorVoltage = elevatorA.getVoltageOutput().addFollowers(elevatorB.getVoltageOutput().invert());
	public final RangeIn<Position> ultrasonicCentimeters = ultrasonic.getDistanceIn();
	
	public final PercentOut leftAVoltageOut = leftA.getVoltageOutput();
	public final PercentOut leftBVoltageOut = leftB.getVoltageOutput();
	public final PercentOut rightAVoltageOut = rightA.getVoltageOutput();
	public final PercentOut rightBVoltageOut = rightB.getVoltageOutput();
	public final PercentOut leftVoltageOut = leftAVoltageOut.addFollowers(leftBVoltageOut);
	public final PercentOut rightVoltageOut = rightAVoltageOut.addFollowers(leftAVoltageOut);
	
	private PIDConstants constants = new PIDConstants(1,0,0,0);
	public final RangeOut<Speed> leftSpeedOutput = leftA.getSpeedOutput(constants).addFollowers(leftB.getSpeedOutput(constants));
	public final RangeOut<Speed> rightSpeedOutput = rightA.getSpeedOutput(constants).addFollowers(rightB.getSpeedOutput(constants));
	


	
	// public AngleIn<Position> angle;
	// public RobotStateEstimator state;

	/*
	 * public RobotStateEstimator setupRobotStateEstimator() { RangeIn<Position> left =
	 * leftA.getPositionInput().setRange(0, 1440).mapToRange(0, Math.PI *
	 * RobotConstants.WheelDiameter); RangeIn<Position> right =
	 * rightA.getPositionInput().setRange(0, 1440).mapToRange(0, Math.PI *
	 * RobotConstants.WheelDiameter); RangeIn<Speed> leftS = leftA.getSpeedInput().setRange(0,
	 * 1440).mapToRange(0, Math.PI * 4); RangeIn<Speed> rightS = rightA.getSpeedInput().setRange(0,
	 * 1440).mapToRange(0, Math.PI * 4); AngleIn<Position> gyro = angle; return new
	 * RobotStateEstimator(new RobotState(), left, right, leftS, rightS, gyro,
	 * RobotConstants.TrackWidth, RobotConstants.TrackLength, RobotConstants.TrackScrub); }
	 */
	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
		// angle = navX.getYawInput();
		// state = setupRobotStateEstimator();
	}

}
