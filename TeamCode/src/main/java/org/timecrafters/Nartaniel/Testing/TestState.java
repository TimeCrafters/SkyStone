package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OrientationSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.ExpansionHubMotorControllerParamsState;


import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TestState extends State {

    private BNO055IMU IMU;


    public TestState(Engine engine) {
        this.engine = engine;
    }

    public void init() {

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);
        while (!IMU.isGyroCalibrated()) {
            sleep(10);
        }

        engine.telemetry.addLine("IMU Calibrated...");
        engine.telemetry.update();
    }

    @Override
    public void exec() throws InterruptedException {
        Orientation orientation = IMU.getAngularOrientation();


        engine.telemetry.addData("x", orientation.firstAngle);
        engine.telemetry.addData("y", orientation.secondAngle);
        engine.telemetry.addData("z", orientation.thirdAngle);
        engine.telemetry.update();

    }
}
