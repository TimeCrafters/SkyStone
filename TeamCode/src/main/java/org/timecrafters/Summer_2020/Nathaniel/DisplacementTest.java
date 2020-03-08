package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.timecrafters.engine.State;

import java.lang.reflect.Array;

public class DisplacementTest extends State {

    private BNO055IMU IMU;
    private Velocity currentVelocityMeasure;
    private Velocity[] VelocityData;

    @Override
    public void init() {
        //IMU init
        //------------------------------------------------------------------------------------------

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);
    }

    @Override
    public void exec() throws InterruptedException {

        currentVelocityMeasure = IMU.getVelocity();
    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("X Velocity", currentVelocityMeasure.xVeloc);
        engine.telemetry.addData("Y Velocity", currentVelocityMeasure.yVeloc);
        engine.telemetry.addData("Z Velocity", currentVelocityMeasure.yVeloc);
    }
}
