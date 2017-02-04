package org.usfirst.frc.team1389.systems;

import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.inputs.software.EncoderIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.hardware.PIDVoltageHardware;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.DriveOut;

public class TestCommandUltra extends Command{


	PercentOut leftVoltageOut;
	PercentOut rightVoltageOut;
	RangeIn<Position> ultrasonicCentimeters;
	double goalDistance;
	private DriveOut<Percent> drive;
	//EncoderIn<Position> leftWheelPosition;
	//EncoderIn<Position> rightWheelPosition;
	public TestCommandUltra(PercentOut leftVoltageOut, PercentOut rightVoltageOut,
			RangeIn<Position> ultrasonicCentimeters, double goalDistance,
			DriveOut<Percent> drive) {
			this.leftVoltageOut = leftVoltageOut;
			this.rightVoltageOut = rightVoltageOut;
			this.ultrasonicCentimeters = ultrasonicCentimeters;
			this.goalDistance = goalDistance;
			this.drive = drive;

	}
	@Override
	protected boolean execute() {
		if(ultrasonicCentimeters.get() < goalDistance){
			drive.set(0.3, 0.3);

		}
		else{
			drive.set(0,0);
		}
		return false;
	}}

	
