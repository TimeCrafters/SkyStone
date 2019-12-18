package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

public class AutomomousState extends State {

    private DcMotor FrontRight;
    private  DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private double DrivePower;
    private int Ticks;
    private StateConfiguration StateConfigeration;
    private String ConfigID;
    private boolean HasRun;

 public AutomomousState (Engine engine, StateConfiguration stateConfiguration, String configID) {
     this.engine =engine;
     StateConfigeration = stateConfiguration;
    ConfigID = configID;
 }



    @Override
    public void init() {
     DrivePower = StateConfigeration.get(ConfigID).variable("power");
     Ticks = StateConfigeration.get(ConfigID).variable("ticks");
        FrontRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");
        FrontLeft = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        BackRight = engine.hardwareMap.dcMotor.get("backRightDrive");
        BackLeft = engine.hardwareMap.dcMotor.get("backLeftDrive");
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    @Override
    public void exec() throws InterruptedException {
     if (!HasRun){
         FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         BackLeft.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
         HasRun = true;
     }
        if (Math.abs(FrontRight.getCurrentPosition())  < Ticks) {
            FrontRight.setPower(-DrivePower);
            FrontLeft.setPower(-DrivePower);
            BackRight.setPower(-DrivePower);
            BackLeft.setPower(-DrivePower);
        } else {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackRight.setPower(0);
            BackLeft.setPower(0);
            setFinished(true);
        }

        engine.telemetry.addData("Ticks", FrontRight.getCurrentPosition());
        engine.telemetry.update();

    }
}
