package org.timecrafters.thomas_thingy.state;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.driver.states.Stop;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class driveforward extends State {  private DcMotor frontleft;
private StateConfiguration statecon;
private String conid;
    private DcMotor frontright;
    private DcMotor backright;
    private DcMotor backleft;
    private DcMotor cranesidemovement;
    private DcMotor craneinoutmovement;
    private double power;
    private int ticks;
    private boolean hasrun;
    public driveforward(){

    }
    public driveforward(Engine engine,StateConfiguration stateConfiguration, String conid ) {
        this.engine=engine;
        statecon=stateConfiguration;
        this.conid=conid;




    }
    @Override
    public void init() {
        frontright=engine.hardwareMap.dcMotor.get("forwardRightDrive");
        frontleft=engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        backleft=engine.hardwareMap.dcMotor.get("backLeftDrive");
        backright=engine.hardwareMap.dcMotor.get("backRightDrive");
        craneinoutmovement=engine.hardwareMap.dcMotor.get("craneY");
        cranesidemovement=engine.hardwareMap.dcMotor.get("craneX");
        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        power=statecon.get(conid).variable("power");
ticks=statecon.get(conid).variable("ticks");


    }

    @Override
    public void exec() throws InterruptedException {
        if (!hasrun){
            frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            hasrun = true;
        }
        if (Math.abs(frontright.getCurrentPosition()) <ticks) {
            frontright.setPower(power);
            frontleft.setPower(power);
            backright.setPower(power);
            backleft.setPower(power);
        }else { frontright.setPower(0);
            frontleft.setPower(0);
            backright.setPower(0);
            backleft.setPower(0);
            setFinished(true);
        }


    }
}

