package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Minibotchallenge2state extends State {
    public Minibotchallenge2state (Engine engine) {this.engine = engine;}

private DcMotor BackRight;
private DcMotor BackLeft;
private double Power;
private boolean PowerChange;
private long Time;


    @Override
    public void init() {
BackRight = engine.hardwareMap.dcMotor.get("Right");
BackLeft = engine.hardwareMap.dcMotor.get("Left");
BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
Power = .5;
PowerChange = false;
    }

    @Override
    public void exec() throws InterruptedException {
        BackLeft.setPower (Power * engine.gamepad1.left_stick_y);
        BackRight.setPower (Power * engine.gamepad1.right_stick_y);

        if (engine.gamepad1.left_bumper){
            if (PowerChange == true){
                if (System.currentTimeMillis() - Time >=250){
                    PowerChange = false;
                }

            } else {
                if (engine.gamepad1.a) {
                    Time = System.currentTimeMillis();
                    Power -= .1;
                    PowerChange = true;
                    if (Power < 0){
                        Power = 0;
                    }
                }
                if (engine.gamepad1.y){
                    Time = System.currentTimeMillis();
                   Power += .1;
                   PowerChange = true;
                   if (Power > 1){
                       Power=1;
                   }
                }
            }

        }


    }
    public void telemetry(){
        engine.telemetry.addData("yay", Power);
    }
}
