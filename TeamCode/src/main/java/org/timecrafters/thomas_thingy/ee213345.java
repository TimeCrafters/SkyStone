package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ee213345 extends State {
    private DcMotor backright;
    private DcMotor backleft;
    private double power;
    private boolean pressedy;
    private boolean presseda;
    private boolean hasrun;
    private int leftlength;
    private int rightlength;
    private double power2;
    private StateConfiguration config;

    public ee213345(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        backleft = engine.hardwareMap.dcMotor.get("backLeftDrive");
        backright = engine.hardwareMap.dcMotor.get("backRightDrive");
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.FORWARD);
        presseda=false;
        pressedy=false;
        config=new StateConfiguration();
    }



    public void exec() throws InterruptedException {

//13.245in for 90

        if (engine.gamepad1.left_bumper && engine.gamepad1.b &&  pressedy==false  ){
            pressedy=true;
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setTargetPosition(150);
            backleft.setPower(.1);
            hasrun = true;

        }
        else if (engine.gamepad1.left_bumper==false && engine.gamepad1.b==false ){pressedy=false;}

        if (engine.gamepad1.left_bumper && engine.gamepad1.x && presseda==false ){
            presseda=true;
            if (!hasrun) {
                backright .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backright.setTargetPosition(100);
                backleft.setTargetPosition(100);
                backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                backleft.setPower(-.1);
                backright.setPower(-.1);
                hasrun = true;

            }
//12.5in = 140encoderticks
        }
        else if(engine.gamepad1.left_bumper==false  && engine.gamepad1.x==false) {presseda=false;}

        engine.telemetry.addData("qwerty123456789" , config.get("thingy; endline").variable("Minecraft"));
        engine.telemetry.addData("qwerty123456789" , config.get("thingy; endline").variable("rust"));
        engine.telemetry.addData("qwerty123456789" , config.get("thingy; endline").variable("fallout"));

    }
}