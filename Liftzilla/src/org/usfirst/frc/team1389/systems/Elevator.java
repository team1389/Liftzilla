package org.usfirst.frc.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.ButtonEnumMap;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class Elevator extends Subsystem{

	private RangeIn<Position> elevatorPosition;
	private RangeIn<Speed> elevatorSpeed;
	private PercentOut voltage;
	private ButtonEnumMap<Height> buttons;
	private ElevatorState state = ElevatorState.ZeroFindingUp;
	private DigitalIn topSensor;
	private DigitalIn bottomSensor;
	public Elevator(RangeIn<Position> elevatorPosition, RangeIn<Speed> elevatorSpeed, PercentOut voltage, ButtonEnumMap<Height> buttons, DigitalIn topSensor, DigitalIn bottomSensor){
		this.elevatorPosition = elevatorPosition;
		this.elevatorSpeed = elevatorSpeed;
		this.voltage = voltage;
		this.buttons = buttons;
		this.topSensor = topSensor;
		this.bottomSensor = bottomSensor;
	}

	private enum ElevatorState{
		ZeroFindingUp, ZeroFindingDown, AcceptingUserInput;
	}
	
	public enum Height{
		Quarter(0.25), Halfway(0.5), ThreeQuarters(0.75);
		public final double percentHeight;
		Height(double percentHeight){
			this.percentHeight = percentHeight;
		}
	}
	
	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		
	}

	private double encoderTicksBottom;
	private double encoderTicksTop;
	@Override
	public void update() {
		if(state == ElevatorState.ZeroFindingUp){
			if(topSensor.get()){
				state = ElevatorState.ZeroFindingDown;
				encoderTicksTop = elevatorPosition.get();
			}
			//TODO: PID control to slow positive speed
		}
		else if(state == ElevatorState.ZeroFindingDown){
			if(bottomSensor.get()){
				state = ElevatorState.AcceptingUserInput;
				encoderTicksTop = elevatorPosition.get();
			}
			//TODO: PID control to slow negative speeed
		}
		else{
			Height toGet = buttons.getCurrentVal();
			//TODO: Pid control to height
		}
	}
}
