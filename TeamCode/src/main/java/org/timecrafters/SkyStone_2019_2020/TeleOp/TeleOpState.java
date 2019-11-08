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
    private boolean FingerDown;
    private boolean FingerTogglePrevious;
    private boolean GrabberClosed;
    private boolean GrabberTogglePrevious;
    private double GrabRotateTargetPos;
    private boolean RightBumpPrevious;
    private boolean LeftBumpPrevious;

    @Override
    public void init() {
        super.init();
        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        GrabRotateTargetPos = 0.85;
        GrabRotateServo.setPosition(GrabRotateTargetPos);
        ArmRight.setPosition(0.5);
        ArmLeft.setPosition(0.5);
    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

//        if (engine.gamepad2.left_bumper) {
//            FingerServoLeft.setPosition(0.65);
//            FingerServoRight.setPosition(0.65);
//        } else if (engine.gamepad2.right_bumper) {
//            FingerServoLeft.setPosition(0);
//            FingerServoRight.setPosition(0);
//
        boolean FingerButton = engine.gamepad2.b;

        if (FingerButton && FingerButton != FingerTogglePrevious && FingerDown) {
            FingerServoLeft.setPosition(0);
            FingerServoRight.setPosition(0);
            FingerDown = false;
        } else if (FingerButton && FingerButton != FingerTogglePrevious && !FingerDown) {
            FingerServoLeft.setPosition(0.65);
            FingerServoRight.setPosition(0.65);
            FingerDown = true;
        }
        FingerTogglePrevious = FingerButton;


        //Crane
        //-----------------------------------------------------------
        if (engine.gamepad2.dpad_right) {
            CraneX.setPower(1);
        } else if (engine.gamepad2.dpad_left) {
            CraneX.setPower(-1);
        } else {
            CraneX.setPower(0);
        }

        if (engine.gamepad2.dpad_down) {
            CraneY.setPower(1);
        } else if (engine.gamepad2.dpad_up) {
            CraneY.setPower(-1);
        } else {
            CraneY.setPower(0);
        }

        //Arms
        //---------------------------------------------------------------------

        boolean ArmButton = engine.gamepad2.x;

        if (ArmButton && ArmButton != GrabberTogglePrevious && GrabberClosed) {
            ArmRight.setPosition(0.5);
            ArmLeft.setPosition(0.5);
            GrabberClosed = false;
        } else if (ArmButton && ArmButton != GrabberTogglePrevious && !GrabberClosed) {
            ArmRight.setPosition(0.95);
            ArmLeft.setPosition(0.0);
            GrabberClosed = true;
        }
        GrabberTogglePrevious = ArmButton;

        //Grip Rollers
        //--------------------------------------------------------------------------

        if (engine.gamepad2.y) {
            ArmGripRight.setPower(1);
            ArmGripLeft.setPower(1);
        } else if (engine.gamepad2.a) {
            ArmGripRight.setPower(-1);
            ArmGripLeft.setPower(-1);
        } else {
            ArmGripRight.setPower(0);
            ArmGripLeft.setPower(0);
        }

        //Grab Rotation Servo
        //--------------------------------------------------------------------------

//        double wrist_stick_pos = 0.5 + (engine.gamepad2.right_stick_x / 2) ;
//        GrabRotateServo.setPosition(wrist_stick_pos);

        boolean rightbump = engine.gamepad2.right_bumper;
        boolean leftbump = engine.gamepad2.left_bumper;

        if (rightbump && rightbump != RightBumpPrevious && GrabRotateTargetPos > 0.15) {
            GrabRotateTargetPos -= .175;
        } else if (leftbump && leftbump != LeftBumpPrevious && GrabRotateTargetPos < 0.85) {
            GrabRotateTargetPos += .175;
        }

        RightBumpPrevious = rightbump;
        LeftBumpPrevious = leftbump;

        GrabRotateServo.setPosition(GrabRotateTargetPos);

        //Lift
        //--------------------------------------------------------------------------
        double lift_stick_y = -engine.gamepad2.left_stick_y;
        int leftLiftPosition = LiftLeft.getCurrentPosition();

        //If the lift is going down, power is significantly reduced do to gravity.
//        if (lift_stick_y > 0) {
//            LiftLeft.setPower(lift_stick_y);
//            LiftRight.setPower(lift_stick_y);
//        } else {
//            LiftLeft.setPower(lift_stick_y * 0.2);
//            LiftRight.setPower(lift_stick_y * 0.2);
//        }

        if (lift_stick_y > 0) {
            LiftRight.setPower(1);
            LiftLeft.setPower(1);
        } else if (engine.gamepad2.left_stick_y == 0) {
            LiftRight.setPower(0);
            LiftLeft.setPower(0);
        } else {
            LiftRight.setPower(-0.7);
            LiftLeft.setPower(-0.7);
        }

        //Drive
        //--------------------------------------------------------------------------
        double powerThrottle =  engine.gamepad1.right_trigger;

        float robotRotation = getRobotRotation();

        if (engine.gamepad1.right_stick_x != 0) {

            //if the right joystick is moved to either side, turn the robot
            if (engine.gamepad1.right_stick_x > 0) {
                DriveForwardLeft.setPower(0.25);
                DriveForwardRight.setPower(-0.25);
                DriveBackLeft.setPower(0.25);
                DriveBackRight.setPower(-0.25);
            } else {
                DriveForwardLeft.setPower(-0.25);
                DriveForwardRight.setPower(0.25);
                DriveBackLeft.setPower(-0.25);
                DriveBackRight.setPower(0.25);
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
        engine.telemetry.addData("Grabber Rotate", GrabRotateTargetPos);
    }
}

