package org.usfirst.frc.team1389.systems;

import com.team1389.control.SmoothSetController;
import com.team1389.control.SynchronousPIDController;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.ButtonEnumMap;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;
import com.team1389.watch.input.listener.NumberInput;

public class Elevator extends Subsystem {

	private RangeIn<Position> elevatorPosition;
	private RangeIn<Speed> elevatorSpeed;
	private PercentOut voltage;
	private ButtonEnumMap<Height> buttons;
	private ElevatorState state = ElevatorState.ZeroFindingUp;
	private DigitalIn topSensor;
	private DigitalIn bottomSensor;
	private SmoothSetController positionController;
	private SynchronousPIDController<Percent, Speed> speedController;

	public Elevator(RangeIn<Position> elevatorPosition, RangeIn<Speed> elevatorSpeed, PercentOut voltage,
			ButtonEnumMap<Height> buttons, DigitalIn topSensor, DigitalIn bottomSensor) {
		this.elevatorPosition = elevatorPosition;
		this.elevatorSpeed = elevatorSpeed;
		this.voltage = voltage;
		this.buttons = buttons;
		this.topSensor = topSensor;
		this.bottomSensor = bottomSensor;
		this.positionController = new SmoothSetController(0.01, 0, 0, 0, 0.1, 0.1, 0.4, elevatorPosition, elevatorSpeed,
				voltage);
		this.speedController = new SynchronousPIDController<Percent, Speed>(0, 0, 0, 0, elevatorSpeed, voltage);
	}

	private enum ElevatorState {
		ZeroFindingUp, ZeroFindingDown, AcceptingUserInput;
	}

	public enum Height {
		Quarter(0.25), Halfway(0.5), ThreeQuarters(0.75), Top(1), Bottom(0);
		public final double percentHeight;

		Height(double percentHeight) {
			this.percentHeight = percentHeight;
		}
	}

	private double goalSpeed;

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		stem.add(speedController.getPIDTuner("Set PID constants"));
		stem.add(new NumberInput("Input Speed", 0, d -> goalSpeed = d));
		stem.add(voltage.getWatchable("volts"));
		return stem;
	}

	@Override
	public String getName() {
		return new String("Elevator");
	}

	@Override
	public void init() {
	}

	private double encoderTicksBottom;
	private double encoderTicksTop;

	@Override
	public void updateTeleop() {
		System.out.println(goalSpeed);
		speedController.setSetpoint(goalSpeed);
		speedController.update();

		/*
		 * if(state == ElevatorState.ZeroFindingUp){ if(topSensor.get()){ state =
		 * ElevatorState.ZeroFindingDown; encoderTicksTop = elevatorPosition.get(); }
		 * speedController.setSetpoint(0.05); } else if(state == ElevatorState.ZeroFindingDown){
		 * if(bottomSensor.get()){ state = ElevatorState.AcceptingUserInput; encoderTicksTop =
		 * elevatorPosition.get(); elevatorPosition.setRange(encoderTicksBottom, encoderTicksTop);
		 * elevatorPosition.mapToRange(0, 1); } speedController.setSetpoint(0.05); } else{ Height
		 * toSet = buttons.getCurrentVal(); positionController.setSetpoint(toSet.percentHeight); }
		 */
	}
}
