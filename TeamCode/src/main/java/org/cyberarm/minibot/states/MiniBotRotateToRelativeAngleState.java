package org.cyberarm.minibot.states;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class MiniBotRotateToRelativeAngleState extends CyberarmStateV2 {
  protected DcMotor leftDrive, rightDrive;
  protected double relativeAngle;
  protected double power;
  private BNO055IMU imu;
  private double initialAngle;
  private double fuzz = 7.0;
  private double targetAngle;

  public MiniBotRotateToRelativeAngleState(double relativeAngle, double power) {
    this.relativeAngle = relativeAngle;
    this.power = Math.abs(power);

    if (relativeAngle < 0) {
      this.power = -power;
    } else {
      this.power = power;
    }

    this.imu = cyberarmEngine.hardwareMap.get(BNO055IMU.class, "imu");
    this.initialAngle = currentAngle();
    this.targetAngle = (((relativeAngle + currentAngle()) + 360) % 360.0);
  }

  @Override
  public void init() {
    leftDrive = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
  }

  @Override
  public void exec() {
    leftDrive.setPower(power);
    rightDrive.setPower(-power);

    if (currentAngle() >= targetAngle - fuzz && currentAngle() <= targetAngle + fuzz) {
      setHasFinished(true);
    }
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("initial angle", initialAngle);
    cyberarmEngine.telemetry.addData("current angle", currentAngle());
    cyberarmEngine.telemetry.addData("target angle", targetAngle);
    cyberarmEngine.telemetry.addData("relative angle", relativeAngle);
  }

  public double fullRangeAngle(double angle) {
    return (angle + 360) % 360.0;
  }

  public double currentAngle() {
    return fullRangeAngle(-imu.getAngularOrientation().firstAngle);
  }
}
