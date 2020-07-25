package org.timecrafters.Summer_2020.Nathaniel.Demo;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.V2.CyberarmStateV2;
import org.timecrafters.Summer_2020.Minibot;

public class ExampleState extends CyberarmStateV2 {

    private Minibot robot;
    private String stateKey;
    private double power;
    private int tickTarget;
    private int tickCurrent;
    private float angleRelative;
    private float angleTarget;

    public ExampleState(Minibot robot, String stateKey) {
        this.robot = robot;
        this.stateKey = stateKey;
    }

    @Override
    public void init() {
        power = robot.stateConfiguration.get(stateKey).variable("power");
        double centimeters = robot.stateConfiguration.get(stateKey).variable("cm");
        tickTarget = robot.getTicksFromCentimeters(centimeters);
    }

    @Override
    public void start() {
        if (robot.stateConfiguration.allow(stateKey)) {
            robot.motorDriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorDriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorDriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorDriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // The target angle is the direction the robot will steers towards when this state is
            // running. Setting it to the robot's rotation at the beginning of the state ensures that
            // the target angle is equal to the robot's forward direction, resulting in a drive
            // path with virtually no drifting.
            angleTarget = robot.imu.getAngularOrientation().firstAngle;

        }
    }

    @Override
    public void exec() {
        if (robot.stateConfiguration.allow(stateKey)) {

            // Calculate the angle between the target (Forward) angle and the robot's current angle.
            // This angle determines how much correction is necessary to steer the robot towards the
            // target angle. Positive values indicate that the current angle is towards the Right of
            // the target. Negative values indicate the current angle is towards the Left.
            angleRelative = robot.getRelativeAngle(angleTarget, robot.imu.getAngularOrientation().firstAngle);

            // The function converting the relative angle to motor powers has been tuned to minimize
            // the oscillations that result from over correction while still being responsive
            // enough to eliminate extreme amounts of drift. The responsiveness of the function
            // was tested by manually setting the target angle with the control joystick and
            // observing how quickly and precisely the robot was able to change direction.

            double powerCorrectionUnlimited = Math.pow(0.03 * angleRelative, 3) + 0.01 * angleRelative;
            double powerUnlimitedRight = power + powerCorrectionUnlimited;
            double powerUnlimitedLeft = power - powerCorrectionUnlimited;

            // These powers are "Unlimited" because they have the potential to exceed the normal
            // power range of -1 to 1. If these were used as they are now, the power ratio between
            // the two drive motors could be warped from what is intended when one or both of the
            // original values are cut off at the limit. The power scalar adjusts the powers
            // to be closer to the base power while maintaining the power ratio necessary to execute
            // the turn.

            double powerScalar = ((2 * power) / (Math.abs(powerUnlimitedRight) + Math.abs(powerUnlimitedLeft)));

            robot.motorDriveRight.setPower(powerUnlimitedRight * powerScalar);
            robot.motorDriveLeft.setPower(powerUnlimitedLeft * powerScalar);


            tickCurrent = Math.abs(robot.motorDriveLeft.getCurrentPosition());
            if (tickCurrent >= tickTarget) {
                robot.motorDriveRight.setPower(0);
                robot.motorDriveLeft.setPower(0);
                setHasFinished(true);
            }
        } else {
            setHasFinished(true);
        }
    }

    @Override
    public void telemetry() {
        cyberarmEngine.telemetry.addData("Current Tick", tickCurrent);
        cyberarmEngine.telemetry.addData("Target Tick", tickTarget);
    }
}
