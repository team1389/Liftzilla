package org.usfirst.frc.team1389.systems;

import com.team1389.hardware.inputs.hardware.PDPHardware;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Value;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class FailureMonitor extends Subsystem{
PDPHardware pdp;
RangeIn <Percent> throttle;
RangeIn <Value> canL, canL2, canR, canR2, elevatorL, elevatorR;
	public FailureMonitor(PDPHardware  pdp, RangeIn<Percent> throttle){
		this.pdp = pdp;
		this.throttle = throttle;
	}
	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return null;
	}

	@Override
	public String getName() {
		return "FailureMonitor";
	}

	@Override
	public void init() {
		canL = pdp.getCurrentIn(9);
		canL2 = pdp.getCurrentIn(5);
		canR = pdp.getCurrentIn(1);
		canR2 = pdp.getCurrentIn(7);
	}

	@Override
	public void update() {
		if(throttle.get)
	}

}
