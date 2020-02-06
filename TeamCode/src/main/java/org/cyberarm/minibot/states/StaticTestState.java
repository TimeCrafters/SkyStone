package org.cyberarm.minibot.states;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class StaticTestState extends CyberarmStateV2 {
  DcMotor leftDrive, rightDrive;

  @Override
  public void init() {
    leftDrive = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
  }

  @Override
  public void start() {
    leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  @Override
  public void exec() {
    leftDrive.setPower(0.0);
    rightDrive.setPower(0.0);

    leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("Left Drive Power", leftDrive.getPower());
    cyberarmEngine.telemetry.addData("Left Drive Position", leftDrive.getCurrentPosition());

    cyberarmEngine.telemetry.addData("Right Drive Power", rightDrive.getPower());
    cyberarmEngine.telemetry.addData("Right Drive Position", rightDrive.getCurrentPosition());
  }
}
