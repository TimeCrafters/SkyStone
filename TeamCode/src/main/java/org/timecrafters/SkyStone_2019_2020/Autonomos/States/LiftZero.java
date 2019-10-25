package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.State;

public class LiftZero extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;


    @Override
    public void init() {

        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {



        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
