package org.timecrafters.SkyStone_2019_2020;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class IMUInit extends Drive {

    private BNO055IMU IMU;

    public IMUInit(Engine engine) {
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
        setFinished(true);
    }
}
