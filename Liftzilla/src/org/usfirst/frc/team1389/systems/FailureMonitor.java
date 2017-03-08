package org.usfirst.frc.team1389.systems;

import com.team1389.hardware.inputs.hardware.PDPHardware;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Value;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;
import com.team1389.watch.info.BooleanInfo;

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
		return stem.put(new BooleanInfo("drive-train-state", ()->throttle.get()==0 && (canL.get()>0 || canL2.get()>0 || canR.get()>0 || canR2.get()>0)), canL.getWatchable("left-motor-current"), canL2.getWatchable("left-two-motor-current"), canR.getWatchable("right-motor-current"), canR2.getWatchable("right-two-motor-current"));
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
			
		
	}

}
