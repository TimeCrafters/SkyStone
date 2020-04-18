package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.engine.V2.CyberarmStateV2;

public class GenericDrive extends CyberarmStateV2 {

    private DcMotor driveLeft;
    private DcMotor driveRight;
    private double power;
    private int ticks;


    public GenericDrive(double power, int ticks) {
        this.power = power;
        this.ticks = ticks;
    }

    public GenericDrive(StateConfiguration stateConfig, String stateConfigKey) {
        power = stateConfig.get(stateConfigKey).variable("power");
        ticks = stateConfig.get(stateConfigKey).variable("ticks");
    }

    @Override
    public void init() {

        driveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
        driveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void start() {
        driveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveRight.setPower(power);
        driveLeft.setPower(power);
    }

    @Override
    public void exec() {
        if (Math.abs(driveLeft.getCurrentPosition()) >= ticks) {
            driveLeft.setPower(0);
            driveRight.setPower(0);
            setHasFinished(true);
        }
    }
}
