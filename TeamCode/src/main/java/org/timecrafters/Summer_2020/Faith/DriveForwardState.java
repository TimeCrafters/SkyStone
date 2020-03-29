package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriveForwardState extends State {

private DcMotor DriveLeft;
private DcMotor DriveRight;
private double Power;
private int Ticks;
private StateConfiguration StateConfig;


    public DriveForwardState(Engine Engine, StateConfiguration stateConfiguration) {
        this.engine = Engine;
        StateConfig = stateConfiguration;
    }

    @Override
    public void init() {
DriveLeft = engine.hardwareMap.dcMotor.get("rightDrive");
DriveRight = engine.hardwareMap.dcMotor.get("leftDrive");
DriveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
Power = StateConfig.get("DriveStateVroom").variable("Power");
Ticks = StateConfig.get("DriveStateVroom").variable("Distance");
    }

    @Override
    public void exec() throws InterruptedException {

        DriveLeft.setPower(Power);
        DriveRight.setPower(Power);

        if (DriveRight.getCurrentPosition()>= Ticks){
            DriveRight.setPower(0);
            DriveLeft.setPower(0);
            setFinished(true);
        }

    }


}
