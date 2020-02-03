package org.cyberarm.minibot.states;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class MiniBotDriveState extends CyberarmStateV2 {
  protected DcMotor leftDrive, rightDrive;
  protected int leftWheelMMDistance, rightWheelMMDistance;
  protected double leftPower, rightPower;
  protected int leftDelta, rightDelta;
  protected int lastLeftPosition, lastRightPosition;

  public MiniBotDriveState(int leftWheelMMDistance, int rightWheelMMDistance, double leftPower, double rightPower) {
    this.leftWheelMMDistance = leftWheelMMDistance;
    this.rightWheelMMDistance = rightWheelMMDistance;

    this.leftPower = Math.abs(leftPower);
    this.rightPower = Math.abs(rightPower);
  }

  public void init() {
    leftDrive = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
  }

  public void start() {
    leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

    leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }


  @Override
  public void exec() {
    Log.i("Autonomous", "Running...");
    if (isAtOrNearDestination(leftDrive, convertMMToTicks(leftWheelMMDistance), leftDelta)) {
//      leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      leftDrive.setPower(0);
    } else {
//      revUpMotor(leftDrive, Range.clip(leftWheelMMDistance / 4, 50, 250), convertMMToTicks(leftWheelMMDistance), 0.1, leftPower);
      setMotorPower(leftDrive, leftPower, leftWheelMMDistance);
    }

    if (isAtOrNearDestination(rightDrive, convertMMToTicks(rightWheelMMDistance), rightDelta)) {
//      rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      rightDrive.setPower(0);
    } else {
//      revUpMotor(rightDrive, Range.clip(rightWheelMMDistance / 4, 50, 250), convertMMToTicks(rightWheelMMDistance), 0.1, rightPower);
      setMotorPower(rightDrive, rightPower, rightWheelMMDistance);
    }

    if (isAtOrNearDestination(leftDrive, convertMMToTicks(leftWheelMMDistance), leftDelta) &&
        isAtOrNearDestination(rightDrive, convertMMToTicks(rightWheelMMDistance), rightDelta)) {
      setHasFinished(true);
    }

    leftDelta = calculateDelta(leftDrive, lastLeftPosition);
    rightDelta = calculateDelta(rightDrive, lastRightPosition);
    lastLeftPosition = leftDrive.getCurrentPosition();
    lastRightPosition = rightDrive.getCurrentPosition();
  }



  @Override
  public void stop() {
    Log.i(TAG, "Stopping...");
    leftDrive.setPower(0.0);
    rightDrive.setPower(0.0);
  }




  public void revUpMotor(DcMotor motor, int revThrough, int targetPosition, double basePower, double maxPower) {
    double power = Range.clip((int) ((double) revThrough / (double) motor.getCurrentPosition()), basePower, maxPower);

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
    return Math.abs(motor.getCurrentPosition()) >= Math.abs(targetPosition);
  }

  // TODO: Verify maths
  public int convertMMToTicks(int distanceInMM) {
    int wheelFullRotationTicks = 540;// 140;
    double wheelFullRotationMM = 319.024; // MM

    return (int)(Math.round((wheelFullRotationMM / wheelFullRotationTicks) * distanceInMM));
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine("Left Motor");
    cyberarmEngine.telemetry.addData(
            "Progress",
            progressBar(20, (double) leftDrive.getCurrentPosition() / (double) convertMMToTicks(leftWheelMMDistance) * 100.0)
    );
    cyberarmEngine.telemetry.addData("Position", leftDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Target Position", convertMMToTicks(leftWheelMMDistance));
    cyberarmEngine.telemetry.addData("Power", leftDrive.getPower());


    cyberarmEngine.telemetry.addLine("");

    cyberarmEngine.telemetry.addLine("Right Motor");
    cyberarmEngine.telemetry.addData(
            "Progress",
            progressBar(20, (double) rightDrive.getCurrentPosition() / (double) convertMMToTicks(rightWheelMMDistance)  * 100.0)
    );
    cyberarmEngine.telemetry.addData("Position", rightDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Target Position", convertMMToTicks(rightWheelMMDistance));
    cyberarmEngine.telemetry.addData("Power", rightDrive.getPower());
  }
}
