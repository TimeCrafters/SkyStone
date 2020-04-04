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
private boolean FirstRun;
private String StateConfigID;

    public DriveForwardState(Engine Engine, StateConfiguration stateConfiguration, String stateConfigID) {
        this.engine = Engine;
        StateConfig = stateConfiguration;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
DriveLeft = engine.hardwareMap.dcMotor.get("rightDrive");
DriveRight = engine.hardwareMap.dcMotor.get("leftDrive");
DriveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
Power = StateConfig.get(StateConfigID).variable("Power");
Ticks = StateConfig.get(StateConfigID).variable("Distance");
FirstRun = true;
    }

    @Override
    public void exec() throws InterruptedException {
if (FirstRun){
    DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    DriveLeft. setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    FirstRun = false;
}

        DriveLeft.setPower(Power);
        DriveRight.setPower(Power);

        if (Math.abs( DriveRight.getCurrentPosition())>= Ticks){
            DriveRight.setPower(0);
            DriveLeft.setPower(0);
            setFinished(true);
        }

    }


}
