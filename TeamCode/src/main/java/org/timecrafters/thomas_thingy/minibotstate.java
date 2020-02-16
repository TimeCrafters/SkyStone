package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;


public class minibotstate extends State {
    private DcMotor backright;
    private DcMotor backleft;
    private double power;
    private boolean pressedy;
    private boolean presseda ;

    public minibotstate(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        backleft = engine.hardwareMap.dcMotor.get("backLeftDrive");
        backright = engine.hardwareMap.dcMotor.get("backRightDrive");
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        power=.5;
        presseda=false;
        pressedy=false;

    }

    public void exec() throws InterruptedException {
        backright.setPower(power*engine.gamepad1.right_stick_y);
          backleft.setPower(power*engine.gamepad1.left_stick_y);

          if (engine.gamepad1.left_bumper && engine.gamepad1.y &&  pressedy==false ){
              pressedy=true;
              power+=.1;
          }
          else if (engine.gamepad1.left_bumper==false && engine.gamepad1.y==false ){pressedy=false;}

        if (engine.gamepad1.left_bumper && engine.gamepad1.a && presseda==false ){
            presseda=true;
            power-=.1 ;
        }
    else if(engine.gamepad1.left_bumper==false  && engine.gamepad1.a==false) {presseda=false;}

    }
    public void telemetry() {
        engine.telemetry.addData("powerrat",power);




    }

}

