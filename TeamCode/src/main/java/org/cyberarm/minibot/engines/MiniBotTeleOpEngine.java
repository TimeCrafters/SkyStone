package org.cyberarm.minibot.engines;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.minibot.states.MiniBotTeleOpState;

@TeleOp(name = "CyberarmEngineV2 MiniBot TeleOp", group = "MINIBOT")
public class MiniBotTeleOpEngine extends CyberarmEngineV2 {
  @Override
  public void setup() {
    BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    parameters.mode = BNO055IMU.SensorMode.IMU;
    parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
    parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    parameters.loggingEnabled = false;

    imu.initialize(parameters);

    addState(new MiniBotTeleOpState());
  }
}
