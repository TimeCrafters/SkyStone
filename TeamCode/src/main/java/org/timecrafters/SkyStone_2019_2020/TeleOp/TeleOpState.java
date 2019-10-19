package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    private double JoystickDegrees;
    private double forwardLeftPower;
    private double forwardRightPower;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;
    private Servo GrabRotateServo;
    private Servo ArmRight;
    private Servo ArmLeft;
    private CRServo ArmGripRight;
    private CRServo ArmGripLeft;
    private DcMotor CraneX;
    private DcMotor CraneY;
    private Servo FingerServoLeft;
    private Servo FingerServoRight;

    @Override
    public void init() {
        super.init();
        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        GrabRotateServo = engine.hardwareMap.servo.get("grabRot");
        ArmRight = engine.hardwareMap.servo.get("armRight");
        ArmLeft = engine.hardwareMap.servo.get("armLeft");
        ArmGripRight = engine.hardwareMap.crservo.get("armGripRight");
        ArmGripLeft = engine.hardwareMap.crservo.get("armGripLeft");
        ArmGripRight.setDirection(CRServo.Direction.REVERSE);

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        CraneY = engine.hardwareMap.dcMotor.get("craneY");

        FingerServoLeft = engine.hardwareMap.servo.get("fingerLeft");
        FingerServoRight = engine.hardwareMap.servo.get("fingerRight");

        FingerServoLeft.setDirection(Servo.Direction.REVERSE);

        GrabRotateServo.setPosition(0.15);
        ArmRight.setPosition(0.4);
        ArmLeft.setPosition(0.5);
    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

        if (engine.gamepad2.left_bumper) {
            FingerServoLeft.setPosition(0.65);
            FingerServoRight.setPosition(0.65);
        } else {
            FingerServoLeft.setPosition(0);
            FingerServoRight.setPosition(0);
        }

        //Crane
        //-----------------------------------------------------------
        if (engine.gamepad2.dpad_right) {
            CraneX.setPower(0.6);
        } else if (engine.gamepad2.dpad_left) {
            CraneX.setPower(-0.6);
        } else {
            CraneX.setPower(0);
        }

        if (engine.gamepad2.dpad_up) {
            CraneY.setPower(0.6);
        } else if (engine.gamepad2.dpad_down) {
            CraneY.setPower(-0.6);
        } else {
            CraneY.setPower(0);
        }

        //Grabber
        //---------------------------------------------------------------------

        if (engine.gamepad2.b) {
            ArmRight.setPosition(0.85);
            ArmLeft.setPosition(0.05);
        } else if (engine.gamepad2.x) {
            ArmRight.setPosition(0.4);
            ArmLeft.setPosition(0.5);
        }

        //Grip Rollers
        //--------------------------------------------------------------------------

        if (engine.gamepad2.a) {
            ArmGripRight.setPower(1);
            ArmGripLeft.setPower(1);
        } else if (engine.gamepad2.y) {
            ArmGripRight.setPower(-1);
            ArmGripLeft.setPower(-1);
        } else {
            ArmGripRight.setPower(0);
            ArmGripLeft.setPower(0);
        }

        //Lift
        //--------------------------------------------------------------------------
        double lift_stick_y = -engine.gamepad2.left_stick_y;
        int leftLiftPosition = LiftLeft.getCurrentPosition();

        //going up
        if (lift_stick_y > 0 && !(LiftRight.getCurrentPosition() >= 216 || leftLiftPosition >= 216)) {
            LiftRight.setPower(lift_stick_y);
            LiftLeft.setPower(lift_stick_y);
            engine.telemetry.addLine("Lift UP!");

            //going down
        } else if (lift_stick_y < 0 && !(LiftRight.getCurrentPosition() <= 0 || leftLiftPosition <= 0)) {

            LiftRight.setPower(lift_stick_y);
            LiftLeft.setPower(lift_stick_y);
            engine.telemetry.addLine("Lift Down!");

        } else {
            LiftRight.setPower(0);
            LiftLeft.setPower(0);
            engine.telemetry.addLine("Lift Still!");
        }


        //Drive
        //--------------------------------------------------------------------------
        double powerThrottle = engine.gamepad1.right_trigger;

        float robotRotation = getRobotRotation();

        if (engine.gamepad1.right_stick_x != 0) {

            //if the right joystick is moved to either side, turn the robot
            if (engine.gamepad1.right_stick_x > 0) {
                DriveForwardLeft.setPower(powerThrottle);
                DriveForwardRight.setPower(-powerThrottle);
                DriveBackLeft.setPower(powerThrottle);
                DriveBackRight.setPower(-powerThrottle);
            } else {
                DriveForwardLeft.setPower(-powerThrottle);
                DriveForwardRight.setPower(powerThrottle);
                DriveBackLeft.setPower(-powerThrottle);
                DriveBackRight.setPower(powerThrottle);
            }

            //If the D pad is pressed, the aline the robot to the appropriate air
        } else if (engine.gamepad1.dpad_up && robotRotation < -2.5) {
            DriveForwardLeft.setPower(powerThrottle);
            DriveForwardRight.setPower(-powerThrottle);
            DriveBackLeft.setPower(powerThrottle);
            DriveBackRight.setPower(-powerThrottle);
        } else if (engine.gamepad1.dpad_up && robotRotation >  2.5) {
            DriveForwardLeft.setPower(-powerThrottle);
            DriveForwardRight.setPower(powerThrottle);
            DriveBackLeft.setPower(-powerThrottle);
            DriveBackRight.setPower(powerThrottle);
        } else if (engine.gamepad1.dpad_left && (robotRotation < -92.5 || robotRotation >= 90)) {
            DriveForwardLeft.setPower(-powerThrottle);
            DriveForwardRight.setPower(powerThrottle);
            DriveBackLeft.setPower(-powerThrottle);
            DriveBackRight.setPower(powerThrottle);
        } else if (engine.gamepad1.dpad_left && -87.5 < robotRotation && robotRotation <= 90) {
            DriveForwardLeft.setPower(-powerThrottle);
            DriveForwardRight.setPower(powerThrottle);
            DriveBackLeft.setPower(-powerThrottle);
            DriveBackRight.setPower(powerThrottle);
        } else if (engine.gamepad1.dpad_right && (robotRotation <= -90 || robotRotation > 92.5)) {
            DriveForwardLeft.setPower(-powerThrottle);
            DriveForwardRight.setPower(powerThrottle);
            DriveBackLeft.setPower(-powerThrottle);
            DriveBackRight.setPower(powerThrottle);
        } else if (engine.gamepad1.dpad_right && -90 <= robotRotation && robotRotation < 87.5) {
            DriveForwardLeft.setPower(powerThrottle);
            DriveForwardRight.setPower(-powerThrottle);
            DriveBackLeft.setPower(powerThrottle);
            DriveBackRight.setPower(-powerThrottle);
        } else if (engine.gamepad1.dpad_down && robotRotation < -2.5) {
            DriveForwardLeft.setPower(-powerThrottle);
            DriveForwardRight.setPower(powerThrottle);
            DriveBackLeft.setPower(-powerThrottle);
            DriveBackRight.setPower(powerThrottle);
        } else if (engine.gamepad1.dpad_down && robotRotation >  2.5 && robotRotation < 177.5) {
            DriveForwardLeft.setPower(powerThrottle);
            DriveForwardRight.setPower(-powerThrottle);
            DriveBackLeft.setPower(powerThrottle);
            DriveBackRight.setPower(-powerThrottle);

        } else if (engine.gamepad1.left_stick_x !=0 || engine.gamepad1.left_stick_y !=0) {

            //if the robot isn't being turned, drive the robot in the direction of the left Joystick

            calcJoystickDegrees();

            forwardLeftPower = powerThrottle * getForwardLeftPower(JoystickDegrees + robotRotation, 0.01);
            forwardRightPower = powerThrottle * getForwardRightPower(JoystickDegrees +  robotRotation, 0.01);

            DriveForwardLeft.setPower(forwardLeftPower);
            DriveForwardRight.setPower(forwardRightPower);
            DriveBackLeft.setPower(forwardRightPower);
            DriveBackRight.setPower(forwardLeftPower);
        } else {
            DriveForwardLeft.setPower(0);
            DriveForwardRight.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
        }
        //------------------------------------------------------------------------

    }

    private void calcJoystickDegrees() {

        double left_stick_y = -engine.gamepad1.left_stick_y;
        double left_stick_x = -engine.gamepad1.left_stick_x;
        double zeroToNinety = Math.toDegrees(Math.atan(left_stick_x / left_stick_y));

        engine.telemetry.addData("raw", zeroToNinety);

        if (left_stick_y == 0 && left_stick_x == 0) {

            JoystickDegrees = 0;

        } else if (left_stick_y > 0) {

            zeroToNinety = -zeroToNinety;
            JoystickDegrees = zeroToNinety;

        } else if (left_stick_y <= 0) {

            if (zeroToNinety > 0) {
                JoystickDegrees = 180 - zeroToNinety;
            } else if (zeroToNinety < 0) {
                JoystickDegrees = -180 - zeroToNinety ;
            } else if (left_stick_x == 0) {
                JoystickDegrees = 180;
            }
        }


    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("JoystickDegrees", JoystickDegrees);
        engine.telemetry.addData("Absolute Degrees", JoystickDegrees + getRobotRotation());
        engine.telemetry.addData("Left Power", getForwardLeftPower(JoystickDegrees + getRobotRotation(), 0.1));
        engine.telemetry.addData("Right Power", getForwardRightPower(JoystickDegrees + getRobotRotation(), 0.1));
    }
}

