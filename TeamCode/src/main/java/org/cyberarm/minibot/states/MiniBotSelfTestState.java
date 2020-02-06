package org.cyberarm.minibot.states;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.V2.CyberarmStateV2;

import java.util.ArrayList;

public class MiniBotSelfTestState extends CyberarmStateV2 {
  private ArrayList<MotorChecker> motorChecks;
  private DcMotor leftDrive, rightDrive;

  @Override
  public void init() {
    motorChecks = new ArrayList<>();

    leftDrive = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");

    motorChecks.add(new MotorChecker(leftDrive, 500));
    motorChecks.add(new MotorChecker(rightDrive, 500));
  }

  @Override
  public void exec() {
    for (MotorChecker motorChecker : motorChecks) {
      if (!motorChecker.isLocked()) {
        continue;
      }

      motorChecker.update();

      if (motorChecker.hasFailed()) {
        panicWithHardwareIssue(motorChecker.getMotor(), motorChecker.getError());
      }
    }
  }

  private void panicWithHardwareIssue(DcMotor motor, String message) {

  }

  private class MotorChecker {
    private DcMotor motor;
    private long startTime, checkTime;
    private boolean failed, locked;
    private String status;
    private int encoderJitter, initialEncoderPosition;

    private MotorChecker(DcMotor motor, long checkTime) {
      this.motor = motor;
      this.checkTime = checkTime;

      this.startTime = System.currentTimeMillis();
      this.status = "Pending...";
      this.encoderJitter = 10;
      this.initialEncoderPosition = motor.getCurrentPosition();
      this.locked = false;
      this.failed = false;
    }

    private void update() {
      if (!locked) {
        locked = true;
      }
    }

    private DcMotor getMotor() { return motor; }
    private String getError() { return status; }
    private String getStatus() { return status; }
    private boolean hasFailed() { return failed; }
    private boolean isLocked() { return locked; }
  }
}
