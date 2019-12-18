package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.cyberarm.engines.CraneDriver;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.state.drive.Drive;

public class TeleOpState extends State {
    private DcMotor FrontRight;
    private  DcMotor FrontLeft;
    private  DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor CraneX;
    private  DcMotor CraneY;
    private double power;
    private double DrivePower;


public TeleOpState(Engine engine) {
    this.engine = engine;
}

    @Override
    public void init() {
        FrontRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");
        FrontLeft = engine.hardwareMap.dcMotor.get ("forwardLeftDrive");
        BackRight = engine.hardwareMap.dcMotor.get("backRightDrive");
        BackLeft = engine. hardwareMap.dcMotor.get("backLeftDrive");
        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        CraneY = engine.hardwareMap.dcMotor.get("craneY");
        power = .25;
    }

    @Override
    public void exec() throws InterruptedException {


        if (engine.gamepad1.right_bumper){
            DrivePower = .5;
        } else {
            DrivePower = 1;
        }

        if (engine.gamepad1.b){
            power = 1;
        }else if (engine.gamepad1.x){
            power = .25;
        }

        if (engine.gamepad1.dpad_left){
            CraneX.setPower(power);
        } else if (engine.gamepad1.dpad_right){
            CraneX.setPower(-power);
        } else {
        CraneX.setPower(0);
        }

        if (engine.gamepad1.dpad_up){
            CraneY.setPower(-power);
        } else if (engine.gamepad1.dpad_down){
            CraneY.setPower(power);
        } else {
            CraneY.setPower(0);
        }
        FrontLeft.setPower(-engine.gamepad1.left_stick_y * DrivePower);
        BackLeft.setPower(-engine.gamepad1.left_stick_y * DrivePower);
        FrontRight.setPower(engine.gamepad1.right_stick_y * DrivePower);
        BackRight.setPower(engine.gamepad1.right_stick_y * DrivePower);
    }
}
