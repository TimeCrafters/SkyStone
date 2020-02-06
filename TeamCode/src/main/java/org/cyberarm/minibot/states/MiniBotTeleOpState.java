package org.cyberarm.minibot.states;

import android.widget.Button;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class MiniBotTeleOpState extends CyberarmStateV2 {
  private DcMotor leftDrive, rightDrive;
  private boolean hijackDriverControl, invertDirection;
  private ControllerButton buttonA, buttonX, buttonY, buttonB, dpadLeft, dpadRight;
  private double powerLimiter = 0.5;
  private CyberarmStateV2 activeHijack;

  @Override
  public void init() {
    leftDrive = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");

    leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

    buttonA = new ControllerButton();
    buttonX = new ControllerButton();
    buttonY = new ControllerButton();
    buttonB = new ControllerButton();
    dpadLeft = new ControllerButton();
    dpadRight = new ControllerButton();

    hijackDriverControl = false;
  }

  @Override
  public void exec() {
    if (!hijackDriverControl) {
      handleDriverControls();
    } else {
      handleAutomaticFunctions();
    }
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("powerLimiter", powerLimiter);
    cyberarmEngine.telemetry.addData("leftDrive", leftDrive.getPower());
    cyberarmEngine.telemetry.addData("rightDrive", rightDrive.getPower());
  }

  private void handleDriverControls() {
    if (!invertDirection) {
      leftDrive.setPower(cyberarmEngine.gamepad1.left_stick_y * -1 * powerLimiter);
      rightDrive.setPower(cyberarmEngine.gamepad1.right_stick_y * -1 * powerLimiter);
    } else {
      leftDrive.setPower(cyberarmEngine.gamepad1.right_stick_y * powerLimiter);
      rightDrive.setPower(cyberarmEngine.gamepad1.left_stick_y * powerLimiter);
    }

    if (buttonA.update(cyberarmEngine.gamepad1.a)) {
      powerLimiter = Range.clip(powerLimiter - 0.1, 0.0, 1.0);
    }

    if (buttonY.update(cyberarmEngine.gamepad1.y)) {
      powerLimiter = Range.clip(powerLimiter + 0.1, 0.0, 1.0);
    }

    if (buttonB.update(cyberarmEngine.gamepad1.b)) {
      invertDirection = !invertDirection;
    }

    if (buttonX.update(cyberarmEngine.gamepad1.x) && !hijackDriverControl) {
      doHijack();
      activeHijack = addParallelState(
              new MiniBotDriveState(
                      1000,
                      1000,
                      1.0 * powerLimiter,
                      1.0 * powerLimiter
              )
      );
    }

    if (dpadLeft.update(cyberarmEngine.gamepad1.dpad_left) && !hijackDriverControl) {
      doHijack();
      activeHijack = addParallelState(
              new MiniBotRotateToRelativeAngleState(
                      -90,
                      1.0 * powerLimiter
              )
      );
    }

    if (dpadRight.update(cyberarmEngine.gamepad1.dpad_right) && !hijackDriverControl) {
      doHijack();
      activeHijack = addParallelState(
              new MiniBotRotateToRelativeAngleState(
                      90,
                      1.0 * powerLimiter
              )
      );
    }
  }

  private void handleAutomaticFunctions() {
    if (hijackDriverControl && activeHijack.getHasFinished()) {
      activeHijack = null;

      returnControlToHumans();
    }
  }

  private void doHijack() {
    stealControlFromHumans();
    haltHazards();
  }

  private void haltHazards() {
    leftDrive.setPower(0.0);
    rightDrive.setPower(0.0);
  }

  private void stealControlFromHumans() {
    hijackDriverControl = true;
  }
  private void returnControlToHumans() {
    hijackDriverControl = false;
  }

  private class ControllerButton {
    private boolean lastButtonState = false;
    private boolean buttonDown = false;

    private boolean update(boolean button) {
      if (button) {
        if (button == lastButtonState) {
          buttonDown = false;
        } else {
          buttonDown = true;
        }
      } else {
        buttonDown = false;
      }

      lastButtonState = button;

      return buttonDown;
    }
  }
}
