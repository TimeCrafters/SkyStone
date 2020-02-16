package org.cyberarm.servo_speed_control;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class InteractingState extends CyberarmStateV2 {
    Servo servo;
    double multiplier = 1.0;
    double multiplierStep = 0.1;
    double value;
    ControllerButton increaseMultiplier;
    ControllerButton decreaseMultiplier;
    ControllerButton updateServo;

    @Override
    public void init() {
        servo = cyberarmEngine.hardwareMap.servo.get("servo");

        increaseMultiplier = new ControllerButton();
        decreaseMultiplier = new ControllerButton();
        updateServo = new ControllerButton();
    }

    @Override
    public void exec() {
        if (increaseMultiplier.update(cyberarmEngine.gamepad1.dpad_up)) {
            multiplier += multiplierStep;
        }

        if (decreaseMultiplier.update(cyberarmEngine.gamepad1.dpad_down)) {
            multiplier -= multiplierStep;
        }

        multiplier = Range.clip(multiplier, 0.0, 1000.0);

        value = (PwmRange.usFrameDefault * multiplier);

        if (updateServo.update(cyberarmEngine.gamepad1.x)) {
            ((PwmControl) servo).setPwmRange(new PwmRange(PwmRange.usPulseLowerDefault, PwmRange.usPulseUpperDefault, value));
        }

        servo.setPosition(cyberarmEngine.gamepad1.left_trigger);
    }

    @Override
    public void telemetry() {
        cyberarmEngine.telemetry.addData("(Target) Position", servo.getPosition());
        cyberarmEngine.telemetry.addData("Value", value);
        cyberarmEngine.telemetry.addData("Multiplier", multiplier);
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
