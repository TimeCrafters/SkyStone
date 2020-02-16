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
Power = .10;
ForwardLeftWheelDrive.setDirection(DcMotorSimple.Direction.REVERSE);
BackwardLeftWheelDrive.setDirection(DcMotorSimple.Direction.REVERSE);
ForwardRightWheelDrive.setDirection(DcMotorSimple.Direction.FORWARD);
BackwardRightWheelDrive.setDirection(DcMotorSimple.Direction.FORWARD);



    }

        @Override
    public void exec() throws InterruptedException {

    if (engine.gamepad1.y) { ;
        ForwardRightWheelDrive. setPower(Power);
    }else {
        ForwardRightWheelDrive.setPower(0.0);
    }

  //  if (engine.gamepad1.b) {
   //     Power = 1.00;
    //    F

    }





}
