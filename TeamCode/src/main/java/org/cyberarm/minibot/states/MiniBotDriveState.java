package org.cyberarm.minibot.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class MiniBotDriveState extends CyberarmStateV2 {
  private DcMotor left, right;
  private int leftWheelMMDistance, rightWheelMMDistance;
  private double leftPower, rightPower;
  private int leftDelta, rightDelta;
  private int lastLeftPosition, lastRightPosition;

  public MiniBotDriveState(int leftWheelMMDistance, int rightWheelMMDistance, double leftPower, double rightPower) {
    this.leftWheelMMDistance = leftWheelMMDistance;
    this.rightWheelMMDistance = rightWheelMMDistance;

    this.leftPower = Math.abs(leftPower);
    this.rightPower = Math.abs(rightPower);
  }

  public void init() {
    left = cyberarmEngine.hardwareMap.dcMotor.get("LeftMotor");
    right = cyberarmEngine.hardwareMap.dcMotor.get("RightMotor");

    left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    left.setDirection(DcMotorSimple.Direction.FORWARD);
    right.setDirection(DcMotorSimple.Direction.REVERSE);

    left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    left.setPower(0);
    right.setPower(0);
  }




  @Override
  public void exec() {
    if (isAtOrNearDestination(left, convertMMToTicks(leftWheelMMDistance), leftDelta) || leftPower == 0.0) {
      left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      left.setPower(0);
    } else {
      revUpMotor(left, Range.clip(leftWheelMMDistance / 4, 50, 250), convertMMToTicks(leftWheelMMDistance), 0.1, leftPower);
    }

    if (isAtOrNearDestination(right, convertMMToTicks(rightWheelMMDistance), rightDelta) || rightPower == 0.0) {
      right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      right.setPower(0);
    } else {
      revUpMotor(right, Range.clip(rightWheelMMDistance / 4, 50, 250), convertMMToTicks(rightWheelMMDistance), 0.1, rightPower);
    }

    if (isAtOrNearDestination(left, convertMMToTicks(leftWheelMMDistance), leftDelta) &&
        isAtOrNearDestination(right, convertMMToTicks(rightWheelMMDistance), rightDelta)) {
      setHasFinished(true);
    }

    leftDelta = calculateDelta(left, lastLeftPosition);
    rightDelta = calculateDelta(right, lastRightPosition);
    lastLeftPosition = left.getCurrentPosition();
    lastRightPosition = right.getCurrentPosition();
  }




  public void revUpMotor(DcMotor motor, int revThrough, int targetPosition, double basePower, double maxPower) {
    double power = Range.clip(revThrough / motor.getCurrentPosition(), basePower, maxPower);

    setMotorPower(motor, power, targetPosition);
  }

  public void setMotorPower(DcMotor motor, double power, int targetPosition) {
    if (targetPosition < motor.getCurrentPosition()) {
      motor.setPower(power * -1);
    } else {
      motor.setPower(power);
    }
  }

  public int calculateDelta(DcMotor motor, int lastPosition) {
    return motor.getCurrentPosition() - lastPosition;
  }

  public boolean isAtOrNearDestination(DcMotor motor, int targetPosition, int delta) {
    return (isAtDestination(motor, targetPosition) || isAtDestination(motor, targetPosition + delta));
  }

  public boolean isAtDestination(DcMotor motor, int targetPosition) {
    if (motor.getPower() <= 0) {
      return motor.getCurrentPosition() <= targetPosition;
    } else {
      return motor.getCurrentPosition() >= targetPosition;
    }
  }

  // TODO: Verify maths
  public int convertMMToTicks(int distanceInMM) {
    int wheelFullRotationTicks = 140;
    double wheelFullRotationMM = 317.5;

    return (int)(Math.round((wheelFullRotationMM / wheelFullRotationTicks) * distanceInMM));
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine("Left Motor");
    progressBar(20, left.getCurrentPosition() / leftWheelMMDistance);
    cyberarmEngine.telemetry.addData("Position", left.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Power", left.getPower());


    cyberarmEngine.telemetry.addLine("");

    cyberarmEngine.telemetry.addLine("Right Motor");
    progressBar(20, right.getCurrentPosition() / rightWheelMMDistance);
    cyberarmEngine.telemetry.addData("Position", right.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Power", right.getPower());
  }
}
