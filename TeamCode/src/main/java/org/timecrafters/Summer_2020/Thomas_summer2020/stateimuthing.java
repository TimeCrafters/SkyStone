package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class stateimuthing extends State {

    private BNO055IMU IMU;


    public stateimuthing(Engine engine) {
   this.engine=engine; }

    @Override
    public void init() {
        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;


        IMU.initialize(parameters);


    }

    @Override
    public void telemetry() {
        engine.telemetry .addData("rotation X", IMU.getAngularOrientation().firstAngle);
        engine.telemetry .addData("rotation Z", IMU.getAngularOrientation().firstAngle);
        engine.telemetry .addData("rotation Y", IMU.getAngularOrientation().firstAngle);


    }

    @Override
    public void exec() throws InterruptedException {

    }
}
