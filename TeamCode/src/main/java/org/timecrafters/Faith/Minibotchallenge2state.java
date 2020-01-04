package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Minibotchallenge2state extends State {
    public Minibotchallenge2state (Engine engine) {this.engine = engine;}

private DcMotor BackRight;
private DcMotor BackLeft;
private double Power;
private boolean ButtonPressed;



    @Override
    public void init() {
BackRight = engine.hardwareMap.dcMotor.get("Right");
BackLeft = engine.hardwareMap.dcMotor.get("Left");
BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
Power = .5;
ButtonPressed = false;
    }

    @Override
    public void exec() throws InterruptedException {
        BackLeft.setPower (Power * engine.gamepad1.left_stick_y);
        BackRight.setPower (Power * engine.gamepad1.right_stick_y);

        if (engine.gamepad1.left_bumper){

                if (engine.gamepad1.a && ButtonPressed == false) {
                    Power -= .1;
                    ButtonPressed = true;
                    if (Power < 0){
                        Power = 0;
                    }
                } else if (engine.gamepad1.y && ButtonPressed == false){
                   Power += .1;
                   ButtonPressed = true;
                   if (Power > 1){
                       Power=1;
                   }
                } else if (engine.gamepad1.a == false && engine.gamepad1.y == false ){ ButtonPressed = false;}
        }




    }
    public void telemetry(){
        engine.telemetry.addData("yay", Power);
    }
}
