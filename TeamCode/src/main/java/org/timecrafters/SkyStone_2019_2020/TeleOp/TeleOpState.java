package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private double JoystickDegrees;
    private double forwardLeftPower;
    private double forwardRightPower;
    private double robotRotationSpeed;
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
    private boolean ArmTogglePrevious;
    private double GrabRotateTargetPos;
    private boolean RightBumpPrevious;
    private boolean LeftBumpPrevious;
    private boolean ResetOrientationBumpPrevious;
    private Rev2mDistanceSensor LeftDistanceSensor;
    private Rev2mDistanceSensor RightDistanceSensor;
    private AutoPlaceX autoPlaceX;
    private AutoPlaceZ autoPlaceZ;
    private AutoPlaceY autoPlaceY;
    private boolean AutoPlaceTogglePrevious;
    private int AutoPlaceStep = 0;
    private boolean RunAutoPlace;
    private int LiftUpperLimit;
    private boolean DisableLiftLimits;


    @Override
    public void init() {
        StateConfig = new StateConfiguration();
        robotRotationSpeed = StateConfig.get(StateConfigID).variable("robotRotationSpeed");
        //based on the end rotation of autonomous
        StartRotationDisplacement = StateConfig.get(StateConfigID).variable("rotDisplace");
        LiftUpperLimit = StateConfig.get(StateConfigID).variable("liftLimit");

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

        CraneY.setDirection(DcMotorSimple.Direction.REVERSE);

        FingerServoLeft = engine.hardwareMap.servo.get("fingerLeft");
        FingerServoRight = engine.hardwareMap.servo.get("fingerRight");

        FingerServoLeft.setDirection(Servo.Direction.REVERSE);

        GrabRotateTargetPos = 1;
        GrabRotateServo.setPosition(GrabRotateTargetPos);
        ArmRight.setPosition(0.5);
        ArmLeft.setPosition(0.5);

//        RightDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");
//        LeftDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceLeft");

//        autoPlaceX = new AutoPlaceX(engine, StateConfig, "TPa");
//        autoPlaceY = new AutoPlaceY(engine, StateConfig);
//        autoPlaceZ = new AutoPlaceZ(engine, StateConfig);
//        autoPlaceX.init();
//        autoPlaceY.init();
//        autoPlaceZ.init();

    }

    public TeleOpState(Engine engine, String stateConfigID) {
        this.engine = engine;
        StateConfigID = stateConfigID;
    }

    @Override
    public void exec() throws InterruptedException {

        //Foundation Clamp
        //-------------------------------------------------------------------

        boolean FingerButton = engine.gamepad1.a;

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
        //------------------------------------------------------------------
        if (engine.gamepad2.dpad_right) {
            CraneX.setPower(0.6);
        } else if (engine.gamepad2.dpad_left) {
            CraneX.setPower(-0.6);
        } else {
            CraneX.setPower(0);
        }

        if (engine.gamepad2.dpad_up) {
            CraneY.setPower(1);
        } else if (engine.gamepad2.dpad_down)  {
            CraneY.setPower(-1);
        } else {
            CraneY.setPower(0);
        }

        //Arms
        //---------------------------------------------------------------------

        boolean ArmButton = engine.gamepad2.x;

        if (ArmButton && ArmButton != ArmTogglePrevious && GrabberClosed) {
            ArmRight.setPosition(0.3);
            ArmLeft.setPosition(0.7);
            GrabberClosed = false;
        } else if (ArmButton && ArmButton != ArmTogglePrevious && !GrabberClosed) {
            ArmRight.setPosition(0.8);
            ArmLeft.setPosition(0.2);
            GrabberClosed = true;
        }
        ArmTogglePrevious = ArmButton;

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

        boolean rightbump = engine.gamepad2.right_bumper;

        if (rightbump && rightbump != RightBumpPrevious) {
            if (GrabRotateTargetPos == 0.75) {
                GrabRotateTargetPos = 1;
            } else {
                GrabRotateTargetPos = 0.75;
            }
        }
        RightBumpPrevious = rightbump;

//
//        if (rightbump && rightbump != RightBumpPrevious && GrabRotateTargetPos > 0.75) {
//            GrabRotateTargetPos -= .125;
//        } else if (leftbump && leftbump != LeftBumpPrevious && GrabRotateTargetPos < 1) {
//            GrabRotateTargetPos += .125;
//        }


        GrabRotateServo.setPosition(GrabRotateTargetPos);

        //Emergency Disable Lift Limits
        //--------------------------------------------------------------------------

        //Should the robot crash durring teleOp and the lift be reinitialized in a non-zero position,
        //this can be used to turn off the limits on the lift, allowing the lift to be moved freely.

        boolean leftBumper = engine.gamepad2.left_bumper;

        if (leftBumper && leftBumper != LeftBumpPrevious) {
            DisableLiftLimits = !DisableLiftLimits;
        }


        //Lift
        //--------------------------------------------------------------------------
        double lift_stick_y = -engine.gamepad2.left_stick_y;
        int liftPosition = -LiftLeft.getCurrentPosition();

        engine.telemetry.addData("Lift Stick", liftPosition);
        //If the lift is going down, power is reduced do to gravity.

        if (lift_stick_y > 0 && (liftPosition < LiftUpperLimit || DisableLiftLimits)) {
            LiftRight.setPower(-1);
            LiftLeft.setPower(-1);
        } else if (lift_stick_y < 0 && (liftPosition > 0 || DisableLiftLimits)){
            LiftRight.setPower(0.5);
            LiftLeft.setPower(0.5);
        } else {
            LiftRight.setPower(0);
            LiftLeft.setPower(0);
        }

        //Emrgency robot orientation reset
        //--------------------------------------------------------------------------

        //If the robot is forced to re-initialize during teleOp, the driver relative controls may be
        //thrown off by the unpredictable starting conditions. This button grabs a new starting orientation
        //for the IMU, allowing the drivers to realign the robot with their perspective.

        //We can not have this trigger accidentally, so both bumpers need to be pressed at the same time to
        //activate the reset.
        boolean resetOrientationBump = (engine.gamepad1.left_bumper && engine.gamepad1.right_bumper);

        if (resetOrientationBump && resetOrientationBump != ResetOrientationBumpPrevious) {
            StartRotationDisplacement = 0;
            setStartOrientation();
        }
        ResetOrientationBumpPrevious = resetOrientationBump;

        //Auto Block Align
        //--------------------------------------------------------------------------

        //Toggle control
//        boolean AutoPlaceButton = engine.gamepad2.b;
//        if (AutoPlaceButton && AutoPlaceButton != AutoPlaceTogglePrevious) {
//            if (AutoPlaceStep == 1) {
//                AutoPlaceStep = 0;
//            } else {
//                AutoPlaceStep = 1;
//            }
//        }
//        AutoPlaceTogglePrevious = AutoPlaceButton;
//
//
//
//        //Run sequence
//        if (AutoPlaceStep == 1) {
//            autoPlaceX.exec();
//            if (autoPlaceX.getIsFinished()) {
//                AutoPlaceStep = 2;
//            }
//        }
//
//        if (AutoPlaceStep == 2) {
//            autoPlaceZ.exec();
//            if (autoPlaceZ.getIsFinished()) {
//                AutoPlaceStep = 3;
//            }
//        }
//
//        if (AutoPlaceStep == 3) {
//            autoPlaceY.exec();
//            if (autoPlaceY.getIsFinished()) {
//                AutoPlaceStep = 0;
//            }
//        }

        //Drive
        //--------------------------------------------------------------------------

        //This exponential function allows for finer control over lower speeds at the expense of
        //
        double powerThrottle =  0.1 * Math.pow(10, engine.gamepad1.right_trigger);

        float robotRotation = getRobotRotation();

        if (engine.gamepad1.right_stick_x != 0) {

            //if the right joystick is moved to either side, turn the robot
            if (engine.gamepad1.right_stick_x > 0) {
                DriveForwardLeft.setPower(robotRotationSpeed);
                DriveForwardRight.setPower(-robotRotationSpeed);
                DriveBackLeft.setPower(robotRotationSpeed);
                DriveBackRight.setPower(-robotRotationSpeed);
            } else {
                DriveForwardLeft.setPower(-robotRotationSpeed);
                DriveForwardRight.setPower(robotRotationSpeed);
                DriveBackLeft.setPower(-robotRotationSpeed);
                DriveBackRight.setPower(robotRotationSpeed);
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

            forwardLeftPower = powerThrottle * getForwardLeftPower(JoystickDegrees - robotRotation, 0.01);
            forwardRightPower = powerThrottle * getForwardRightPower(JoystickDegrees -  robotRotation, 0.01);

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
//        engine.telemetry.addData("JoystickDegrees", JoystickDegrees);
//        engine.telemetry.addData("Absolute Degrees", JoystickDegrees - getRobotRotation());
//        engine.telemetry.addData("Left Power Function", getForwardLeftPower(JoystickDegrees - getRobotRotation(), 0.1));
//        engine.telemetry.addData("Right Power Function", getForwardRightPower(JoystickDegrees - getRobotRotation(), 0.1));
//        engine.telemetry.addData("Left Power Real", DriveForwardLeft.getPower());
//        engine.telemetry.addData("Right Power Real", DriveForwardRight.getPower());
//        engine.telemetry.addData("Grabber Rotate", GrabRotateTargetPos);
    }
}

