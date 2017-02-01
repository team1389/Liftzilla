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

	public ComplexElevator(PercentOut elevatorVoltage, RangeIn<Speed> elevatorSpeed, RangeIn<Position> elevatorPos,
			DigitalIn top, DigitalIn bottom) {
		elevatorPID = new SynchronousPIDController<>(RobotConstants.ElevatorUpPID, elevatorSpeed, elevatorVoltage);
		elevatorPID.setInputRange(-1000, 1000);
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
		scheduler.schedule(
				CommandUtil.combineSequential(new ZeroDown(-600), new ZeroUp(600), CommandUtil.createCommand(() -> {
					elevatorPID.setSetpoint(0);
					return true;
				})));
	}

	@Override
	public void update() {
		elevatorPID.update();
		scheduler.update();
	}

	private class ZeroUp extends Command{

		public ZeroUp(int upSetpoint) {
			// TODO Auto-generated constructor stub
		}
		@Override
		public void initialize() {
			System.out.println("zeroing up");
			elevatorSpeedSetter.set(600);
			elevatorPID.setPID(RobotConstants.ElevatorUpPID);
		}
		@Override
		protected boolean execute() {
			return topSwitch.get();
		}

	}

	private class ZeroDown extends Command {
		public ZeroDown(int downSetpoint) {
		}

		@Override
		public void initialize() {
			System.out.println("zeroing down");
			elevatorSpeedSetter.set(-600);
			elevatorPID.setPID(RobotConstants.ElevatorDownPID);
		}

		@Override
		protected boolean execute() {
			return botSwitch.get();
		}
	}
}
