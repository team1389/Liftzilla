package org.usfirst.frc.team1389.systems;

import java.util.concurrent.CompletableFuture;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.configuration.PIDInput;
import com.team1389.control.SmoothSetController;
import com.team1389.hardware.inputs.hardware.AnalogUltrasonicHardware;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.util.unit_conversion.dimensions.DistanceUnit;
import com.team1389.watch.CompositeWatchable;

public class UltrasonicMaintainDistance extends Command{

	
	private SmoothSetController controllerLeft;
	private SmoothSetController controllerRight;
	private AnalogUltrasonicHardware ultrasonic;
	private double centimeters = 0; //This might need to be synchronized
	private double goalDistance = 0;
	public UltrasonicMaintainDistance(AnalogUltrasonicHardware ultrasonic, 
			PositionEncoderIn positionLeft, PositionEncoderIn positionRight,  
			RangeIn<Speed> velLeft, RangeIn<Speed> velRight, 
			PercentOut outputLeft, PercentOut outputRight, double distance){
		
		this.goalDistance  = distance;
		PIDConstants defaultPID = new PIDConstants(0,0,0,0);
		controllerLeft = new SmoothSetController(defaultPID, 10, 10, 50, positionLeft.convertValuesTo(DistanceUnit.Centimeters), velLeft.mapToRange(0, 1).scale(100).scale(Math.PI * 6), outputLeft);
		controllerRight = new SmoothSetController(defaultPID, 10, 10, 50, positionRight.convertValuesTo(DistanceUnit.Centimeters), velRight.mapToRange(0, 1).scale(100).scale(Math.PI * 6), outputRight);
		this.ultrasonic = ultrasonic;
		
		
		CompletableFuture.runAsync(() -> CommandUtil.executeCommand(new ultrasonicSetterAndGetter(), 20));
	}

	private class ultrasonicSetterAndGetter extends Command{
		RangeIn<Position> distanceStream = ultrasonic.getDistanceIn();
		@Override
		protected boolean execute() {
			centimeters = distanceStream.get();
			return false;
		}
		//TODO: close on command end
		
	}
	
	public CompositeWatchable getPidTuner(){
		return CompositeWatchable.of("Ultrasonic Tuner", new PIDInput("Ultrasonic Tuner", new PIDConstants(0,0,0,0), true, 
				(PIDConstants constants) -> {controllerLeft.setPID(constants); controllerRight.setPID(constants);}
				).getSubWatchables(CompositeWatchable.makeStem()));
	}
	
	@Override
	protected boolean execute() {
		if(goalDistance != controllerLeft.getError()){
			controllerLeft.setSetpoint(centimeters);
		}
		if(goalDistance != controllerRight.getError()){
			controllerRight.setSetpoint(centimeters);
		}
		controllerLeft.update();
		controllerRight.update();
		return false;
	}

	


}
