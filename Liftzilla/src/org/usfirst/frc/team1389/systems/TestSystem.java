package org.usfirst.frc.team1389.systems;

import com.team1389.hardware.inputs.hardware.AnalogUltrasonicHardware;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class TestSystem extends Subsystem{

	UltrasonicMaintainDistance ultraMaintainDistance;
	public TestSystem(AnalogUltrasonicHardware ultrasonic, 
			PositionEncoderIn positionLeft, PositionEncoderIn positionRight,  
			RangeIn<Speed> velLeft, RangeIn<Speed> velRight, 
			PercentOut outputLeft, PercentOut outputRight, double distance){
		ultraMaintainDistance = new UltrasonicMaintainDistance(ultrasonic, positionRight, positionRight, velRight, velRight, outputRight, outputRight, distance);
	}
	
	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		stem.add(ultraMaintainDistance.getPidTuner());
		return stem;
	}

	@Override
	public String getName() {
		return "Ultrasonic Test Drive";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTeleop() {
		ultraMaintainDistance.execute();
	}

}
