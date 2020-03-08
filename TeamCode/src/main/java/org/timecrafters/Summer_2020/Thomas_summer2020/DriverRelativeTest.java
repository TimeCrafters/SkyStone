package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriverRelativeTest extends State {

private DcMotor ForwardRightWheelDrive;
private DcMotor ForwardLeftWheelDrive;
private DcMotor BackwardRightWheelDrive;
private DcMotor BackwardLeftWheelDrive;
private double Power;
public DriverRelativeTest(Engine engine) { this.engine = engine;}
private float targetdirecd;

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

        if (engine.gamepad1.y){
            targetdirecd=0;
        } else if ( engine.gamepad1.b){
            targetdirecd=90;
        } else if (engine.gamepad1.x){
            targetdirecd=-90;
        } else if ( engine.gamepad1.a){
            targetdirecd=180;
        }

    if (targetdirecd>=-45 &&  targetdirecd <=45) { ;
        ForwardRightWheelDrive. setPower(Power);
        ForwardLeftWheelDrive.setPower(Power);
        BackwardRightWheelDrive.setPower(Power);
        BackwardLeftWheelDrive.setPower(Power);

    }

    if (targetdirecd>=45  && targetdirecd <= 135) { ;
        ForwardRightWheelDrive. setPower(-Power);
        ForwardLeftWheelDrive.setPower(Power);
        BackwardRightWheelDrive.setPower(Power);
        BackwardLeftWheelDrive.setPower(-Power);
    }
    if (targetdirecd<=-135 && targetdirecd>= 135) { ;
        ForwardRightWheelDrive. setPower(-Power);
        ForwardLeftWheelDrive.setPower(-Power);
        BackwardRightWheelDrive.setPower(-Power);
        BackwardLeftWheelDrive.setPower(-Power);
    }
    if (targetdirecd<=-45 && targetdirecd>= -135) { ;
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
