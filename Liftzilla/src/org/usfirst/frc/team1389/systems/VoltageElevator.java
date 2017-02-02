package org.usfirst.frc.team1389.systems;

import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class VoltageElevator extends Subsystem {

	private PercentOut elevatorMotors;
	private PercentIn joyVal;
	private RangeIn<Speed> encoderSpeed;
	private RangeIn<Position> encoderPosition;

	public VoltageElevator(PercentOut elevatorMotors, PercentIn joyVal, RangeIn<Speed> encoderSpeed,
			RangeIn<Position> encoderPosition) {
		this.elevatorMotors = elevatorMotors;
		this.joyVal = joyVal;
		this.encoderPosition = encoderPosition;
		this.encoderSpeed = encoderSpeed;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(joyVal.getWatchable("joystick value"),
		elevatorMotors.getWatchable("elevator output voltage"),
		encoderSpeed.getWatchable("speed"), encoderPosition.getWatchable("pos"));
	}

	@Override
	public String getName() {
		return "Voltage Elevator System";
	}

	@Override
	public void init() {

	}

	@Override
	public void updateTeleop() {
		elevatorMotors.set(joyVal.get());
	}

}
