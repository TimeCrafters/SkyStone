package org.timecrafters.Nartaniel.Training;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TrainingTeleOpState extends State {

    private DcMotor DriveFrontRight;
    private DcMotor DriveFrontLeft;
    private DcMotor DriveBackRight;
    private DcMotor DriveBackLeft;
    private DcMotor CraneX;
    private DcMotor CraneY;
    private double CranePower = 1;
    private double DrivePower =1;

    //Constructor
    public TrainingTeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        DriveFrontRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");
        DriveFrontLeft = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        DriveBackRight = engine.hardwareMap.dcMotor.get("backRightDrive");
        DriveBackLeft = engine.hardwareMap.dcMotor.get("backLeftDrive");


        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        CraneY = engine.hardwareMap.dcMotor.get("craneY");
        CranePower =1;
    }

    @Override
    public void exec() throws InterruptedException {

        if (engine.gamepad1.right_bumper) {
            DrivePower = .3;
        } else {
            DrivePower = 1;
        }

        if (engine.gamepad1.dpad_left){
            CraneX.setPower(CranePower);
        } else if (engine.gamepad1.dpad_right) {
             CraneX.setPower(-CranePower);
        } else {
            CraneX.setPower(0);
        }

        DriveFrontLeft.setPower(engine.gamepad1.left_stick_y * DrivePower);
        DriveBackLeft.setPower(engine.gamepad1.left_stick_y * DrivePower);
        DriveFrontRight.setPower(engine.gamepad1.right_stick_y * DrivePower);
        DriveBackRight.setPower(engine.gamepad1.right_stick_y * DrivePower);

    }
}
