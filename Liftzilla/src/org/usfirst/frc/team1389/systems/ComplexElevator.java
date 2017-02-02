package org.usfirst.frc.team1389.systems;

import org.usfirst.frc.team1389.robot.RobotConstants;

import com.team1389.command_framework.CommandScheduler;
import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.control.SynchronousPIDController;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class ComplexElevator extends Subsystem {
	private RangeOut<Speed> elevatorSpeedSetter;
	private SynchronousPIDController<Percent, Speed> elevatorPID;
	private DigitalIn topSwitch, botSwitch;
	private RangeIn<Position> elevatorPos;
	private CommandScheduler scheduler;
	private double topPos, botPos;

	public ComplexElevator(PercentOut elevatorVoltage, RangeIn<Speed> elevatorSpeed, RangeIn<Position> elevatorPos,
			DigitalIn top, DigitalIn bottom) {
		elevatorPID = new SynchronousPIDController<>(RobotConstants.ElevatorUpPID, elevatorSpeed, elevatorVoltage);
		elevatorPID.setInputRange(-2000, 2000);
		elevatorSpeedSetter = elevatorPID.getSetpointSetter();
		this.topSwitch = top;
		this.botSwitch = bottom;
		this.elevatorPos = elevatorPos;
		this.scheduler = new CommandScheduler();
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(elevatorSpeedSetter.getWatchable("lastSetSpeed"), elevatorPID.getSource().getWatchable("speed"),
				elevatorPID.getOutput().getWatchable("lastSetVoltage"), scheduler);
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
			elevatorPos.adjustRange(botPos, topPos, 0, 1);
			return true;
		});
		scheduler.schedule(
				CommandUtil.combineSequential(new ZeroDown(), new ZeroUp(), gatherRangeData, new StopElevator()));
	}

	@Override
	public void update() {
		elevatorPID.update();
		scheduler.update();
	}

	private class StopElevator extends Command {

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
			elevatorPID.setPID(RobotConstants.ElevatorUpPID);
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
			elevatorPID.setPID(RobotConstants.ElevatorDownPID);
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
