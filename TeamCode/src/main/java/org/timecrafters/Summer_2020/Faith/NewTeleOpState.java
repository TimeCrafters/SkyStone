package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class NewTeleOpState extends State {

private DcMotor ForwardRightWheelDrive;
private DcMotor ForwardLeftWheelDrive;
private DcMotor BackwardRightWheelDrive;
private DcMotor BackwardLeftWheelDrive;
private double Power;
public NewTeleOpState (Engine engine) { this.engine = engine;}

    @Override
    public void init() {
ForwardRightWheelDrive = engine.hardwareMap.dcMotor.get("forwardRightDrive");
BackwardRightWheelDrive = engine.hardwareMap.dcMotor.get("backRightDrive");
BackwardLeftWheelDrive = engine.hardwareMap.dcMotor.get("backLeftDrive");
ForwardLeftWheelDrive = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
Power = 1.00;
ForwardLeftWheelDrive.setDirection(DcMotorSimple.Direction.FORWARD);
BackwardLeftWheelDrive.setDirection(DcMotorSimple.Direction.FORWARD);
ForwardRightWheelDrive.setDirection(DcMotorSimple.Direction.REVERSE);
BackwardRightWheelDrive.setDirection(DcMotorSimple.Direction.REVERSE);



    }

        @Override
    public void exec() throws InterruptedException {

    if (engine.gamepad1.y) { ;
        ForwardRightWheelDrive. setPower(Power);
        ForwardLeftWheelDrive.setPower(Power);
        BackwardRightWheelDrive.setPower(Power);
        BackwardLeftWheelDrive.setPower(Power);

    }

    if (engine.gamepad1.b) { ;
        ForwardRightWheelDrive. setPower(-Power);
        ForwardLeftWheelDrive.setPower(Power);
        BackwardRightWheelDrive.setPower(Power);
        BackwardLeftWheelDrive.setPower(-Power);
    }
    if (engine.gamepad1.a) { ;
        ForwardRightWheelDrive. setPower(-Power);
        ForwardLeftWheelDrive.setPower(-Power);
        BackwardRightWheelDrive.setPower(-Power);
        BackwardLeftWheelDrive.setPower(-Power);
    }
    if (engine.gamepad1.x) { ;
        ForwardRightWheelDrive. setPower(Power);
        ForwardLeftWheelDrive.setPower(-Power);
        BackwardRightWheelDrive.setPower(-Power);
        BackwardLeftWheelDrive.setPower(Power);
    }

    if (!engine.gamepad1.b &&!engine.gamepad1.y &&!engine.gamepad1.x &&!engine.gamepad1.a ){
        ForwardRightWheelDrive.setPower(0.0);
        ForwardLeftWheelDrive.setPower(0.0);
        BackwardLeftWheelDrive.setPower(0.0);
        BackwardRightWheelDrive.setPower(0.0);
    }





}
}
