package org.usfirst.frc.team1389.systems;

import org.usfirst.frc.team1389.robot.RobotConstants;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.control.SmoothSetController;
import com.team1389.control.SynchronousPIDController;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.ButtonEnumMap;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class Elevator extends Subsystem {
	private RangeOut<Speed> elevatorSpeedSetter;
	private SynchronousPIDController<Percent, Speed> elevatorPID;
	private DigitalIn topSwitch, botSwitch, zero;
	private RangeIn<Position> elevatorPos;
	private ButtonEnumMap<Height> heightmap;
	private SynchronousPIDController<Percent, Position> controller;
	private SmoothSetController smoothSetController;
	private double elevatorPositionSetpoint;
	private double topPos, botPos;
	public Elevator(PercentOut elevatorVoltage, RangeIn<Speed> elevatorSpeed, RangeIn<Position> elevatorPos,
			DigitalIn top, DigitalIn bottom, DigitalIn zero, ButtonEnumMap<Height> heightmap) {
		PercentOut voltage = elevatorVoltage.copy().offset(.15);
		elevatorPID = new SynchronousPIDController<>(RobotConstants.ElevatorSpeedPID, elevatorSpeed, voltage);
		elevatorPID.setInputRange(-2000, 2000);
		elevatorSpeedSetter = elevatorPID.getSetpointSetter();
		this.topSwitch = top;
		this.smoothSetController = new SmoothSetController(RobotConstants.ElevatorSmoothSetPID,
				RobotConstants.ElevatorMaxAccel, RobotConstants.ElevatorMaxAccel, RobotConstants.ElevatorMaxVel,
				elevatorPos, elevatorSpeed.copy().mapToRange(0, 1).scale(Math.PI * 2.25), voltage);
		this.botSwitch = bottom;
		this.elevatorPos = elevatorPos;
		this.heightmap = heightmap;
		this.zero = zero;

		controller = new SynchronousPIDController<>(RobotConstants.ElevatorSmoothSetPID, elevatorPos, voltage);
	}

	public enum Height {
		BOTTOM(0), ONE_TOTE(13.75), TWO_TOTE(25.75);
		public final double height;

		private Height(double height) {
			this.height = height;
		}
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(elevatorSpeedSetter.getWatchable("lastSetSpeed"), elevatorPID.getSource().getWatchable("speed"),
				elevatorPID.getOutput().getWatchable("lastSetVoltage"), getScheduler(), elevatorPos.getWatchable("pos"),
				heightmap.getWatchable("height"), smoothSetController.getPIDTunerWithSetpoint("speedPID"));
	}

	@Override
	public String getName() {
		return "elevatorComp";
	}

	@Override
	public void init() {
		initZeroMode();
	}

	public void initZeroMode() {
		Command gatherRangeData = CommandUtil.createCommand(() -> {
			System.out.println("Endstops hit: [" + botPos + "," + topPos + "]");
			elevatorPos.adjustRange(botPos, topPos, 0, RobotConstants.ElevatorHeightInches);
			return true;
		});
		schedule(CommandUtil.combineSequential(new ZeroDown(), new ZeroUp(), gatherRangeData, new StopElevator()));
	}

	@Override
	public void update() {

		if (getScheduler().isFinished()) {
			smoothSetController.update();
			// elevatorPositionSetpoint = heightmap.getVal().height;
			// controller.setSetpoint(elevatorPositionSetpoint);
			if (zero.get()) {
				initZeroMode();
			}
		} else {
			elevatorPID.update();
		}
	}

	public void setPositionSetpoint(double setpoint) {
		elevatorPositionSetpoint = setpoint;
	}

	public class StopElevator extends Command {

		@Override
		public void initialize() {
			System.out.println("stopping elevator");
			elevatorSpeedSetter.set(0.0);
		}

		@Override
		protected boolean execute() {
			elevatorPID.update();
			return elevatorPID.onTarget(.01);
		}

	}

	private static final double zeroSpeedRPM = 1000;

	private class ZeroUp extends Command {

		@Override
		public void initialize() {
			System.out.println("elevator zeroing up");
			elevatorSpeedSetter.set(zeroSpeedRPM);
		}

		@Override
		protected boolean execute() {
			return topSwitch.get();
		}

		@Override
		protected void done() {
			topPos = elevatorPos.get();
		}

	}

	private class ZeroDown extends Command {
		@Override
		public void initialize() {
			System.out.println("elevator zeroing down");
			elevatorSpeedSetter.set(-zeroSpeedRPM);
		}

		@Override
		protected boolean execute() {
			return botSwitch.get();
		}

		@Override
		protected void done() {
			botPos = elevatorPos.get();
		}
	}
}
