package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class DriveTrainTest extends Drive {

    private double Throttle;
    private boolean FirstRun;
    private float DriveDirection;
    private double Inches;
    private int Ticks;

    public DriveTrainTest(Engine engine, double power, double inches, float driveDirection) {
        this.engine = engine;
        this.Throttle = power;
        this.Inches = inches;
        this.DriveDirection = driveDirection;
    }

    @Override
    public void init() {
        super.init();
        Ticks = InchesToTicks(Inches, 4, 2);
        FirstRun = true;
    }


    @Override
    public void exec() throws InterruptedException {

        if (FirstRun) {
            DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            DriveForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FirstRun = false;
        }

        engine.telemetry.addData("Current Tick", DriveForwardLeft.getCurrentPosition());
        engine.telemetry.addData("Target", Ticks);

        if ( Math.abs(DriveForwardLeft.getCurrentPosition()) <= Ticks) {
            DriveForwardLeft.setPower(getForwardLeftPower(DriveDirection, 0.05)*Throttle);
            DriveForwardRight.setPower(getForwardRightPower(DriveDirection, 0.05)*Throttle);
            DriveBackLeft.setPower(getForwardRightPower(DriveDirection, 0.05)*Throttle);
            DriveBackRight.setPower(getForwardLeftPower(DriveDirection, 0.05)*Throttle);
        } else {
            DriveForwardLeft.setPower(0);
            DriveForwardRight.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
            sleep(100);
            setFinished(true);
        }

    }

    private int InchesToTicks(double distanceIN, double whealDiameter, double gearRatio) {
        int ticks = (int) (((distanceIN * 288) / (whealDiameter * Math.PI)) / gearRatio);
        return ticks;
    }
}
