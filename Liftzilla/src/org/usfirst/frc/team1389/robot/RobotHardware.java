package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.hardware.GyroHardware;
import com.team1389.hardware.inputs.hardware.NavXHardware;
import com.team1389.hardware.inputs.hardware.PotentiometerHardware;
import com.team1389.hardware.inputs.hardware.SwitchHardware;
import com.team1389.hardware.outputs.hardware.CANTalonGroup;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.Analog;
import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.hardware.registry.port_types.DIO;
import com.team1389.hardware.registry.port_types.PWM;

import edu.wpi.first.wpilibj.SPI;

/**
 * responsible for initializing and storing hardware objects defined in {@link RobotLayout}
 * 
 * @author amind
 * @see RobotLayout
 * @see RobotMap
 */
public class RobotHardware extends RobotLayout {

    
    /**
     * Initializes robot hardware by subsystem. <br>
     * note: use this method as an index to show hardware initializations that occur, and to find the init code for a particular system's hardware
     */
    protected RobotHardware() {
        registry = new Registry();
        navX = new NavXHardware(SPI.Port.kMXP, registry);
        
    }
    public void initDrive(){
        left = new CANTalonHardware(false, false, new CAN(can_LEFT_MOTOR_A), registry);
        right = new CANTalonHardware(false, false, new CAN(can_RIGHT_MOTOR_A), registry);
    }
    
    public void initVictor(){
        leftVictor = new VictorHardware(false, new PWM(victor_LEFT), registry);
        rightVictor = new VictorHardware(false, new PWM(victor_RIGHT), registry);
    }

    
    
    public Registry getRegistry() {
        return registry;
    }
}