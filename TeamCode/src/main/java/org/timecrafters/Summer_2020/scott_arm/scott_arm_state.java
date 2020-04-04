package org.timecrafters.Summer_2020.scott_arm;
import com.qualcomm.robotcore.hardware.Servo;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_arm_state extends State {

    public scott_arm_state (Engine engine) { this.engine = engine;}

    // names in config
    private String szRotateZ   = "z";
    private String szShoulder  = "s";
    private String szElbow     = "e";
    private String szClamp     = "c";

    // pointers to hardware
    private Servo pRotateZ;
    private Servo pShoulder;
    private Servo pElbow;
    private Servo pClamp;


    // track location
    private double dRotateZ;
    private double dShoulder;
    private double dElbow;
    private double dClamp;
    private double dServoStepSize = 0.05;

    // track time
    private long lCurrentTime;
    private long lRotateZ;
    private long lShoulder;
    private long lElbow;
    private long lClamp;
    private long lServoStepTime = 100;

    @Override
    public void init() {
        // get pointers for hardware
        pRotateZ = engine.hardwareMap.servo.get(szRotateZ);
        pShoulder = engine.hardwareMap.servo.get(szShoulder);
        pElbow = engine.hardwareMap.servo.get(szElbow);
        pClamp = engine.hardwareMap.servo.get(szClamp);

        // set initial positions
        pRotateZ.setPosition(0.5);
        pShoulder.setPosition(0.5);
        pElbow.setPosition(0.5);
        pClamp.setPosition(0.5);

        // get positions that were just written
        dRotateZ = pRotateZ.getPosition();
        dShoulder = pShoulder.getPosition();
        dElbow = pElbow.getPosition();
        dClamp = pClamp.getPosition();

        // time variables
        lCurrentTime = System.currentTimeMillis();
        lRotateZ = lCurrentTime;
        lShoulder = lCurrentTime;
        lElbow = lCurrentTime;
        lClamp = lCurrentTime;


    }

    @Override
    public void exec() throws InterruptedException {
        lCurrentTime = System.currentTimeMillis();
        if(engine.gamepad1.b){ // ................................... right most z position
                pRotateZ.setPosition(1.0);
            }
        else if(engine.gamepad1.y){ // .............................. center everything
            pRotateZ.setPosition((0.5));
            pShoulder.setPosition(0.5);
            pElbow.setPosition(0.5);
            pClamp.setPosition(0.5);
        }
        else if(engine.gamepad1.x){ // .............................. left most z position
            pRotateZ.setPosition((0.0));
        }

        if(engine.gamepad1.dpad_right &&
                lCurrentTime > lRotateZ+lServoStepTime){ //.......... step right
            dRotateZ = pRotateZ.getPosition();
            if(dRotateZ < 1.0) {
                pRotateZ.setPosition(dRotateZ += dServoStepSize);
                lRotateZ = lCurrentTime;
            }
        }
        else if(engine.gamepad1.dpad_left &&
                lCurrentTime > lRotateZ+lServoStepTime){ //.......... step left
            dRotateZ = pRotateZ.getPosition();
            if(dRotateZ > 0.0) {
                pRotateZ.setPosition(dRotateZ -= dServoStepSize);
                lRotateZ = lCurrentTime;
            }
        }

        if(engine.gamepad1.dpad_up &&
                lCurrentTime > lShoulder+lServoStepTime){ //......... step up
            dShoulder = pShoulder.getPosition();
            if(dShoulder < 1.0) {
                pShoulder.setPosition(dShoulder += dServoStepSize);
                pElbow.setPosition(dElbow -= dServoStepSize);
                lShoulder = lCurrentTime;
            }
        }
        else if(engine.gamepad1.dpad_down &&
                lCurrentTime > lShoulder+lServoStepTime){ //......... step down
            dShoulder = pShoulder.getPosition();
            if(dShoulder > 0.0) {
                pShoulder.setPosition(dShoulder -= dServoStepSize);
                pElbow.setPosition(dElbow += dServoStepSize);
                lShoulder = lCurrentTime;
            }
        }

        if(engine.gamepad1.right_bumper &&
                lCurrentTime > lClamp+lServoStepTime){ //............ open clamp
            dClamp = pClamp.getPosition();
            if(dClamp < 1.0) {
                pClamp.setPosition(dClamp += dServoStepSize);
                lClamp = lCurrentTime;
            }
        }
        else if(engine.gamepad1.left_bumper &&
                lCurrentTime > lClamp+lServoStepTime){ //............ close clamp
            dClamp = pClamp.getPosition();
            if(dClamp > 0.15) {
                pClamp.setPosition(dClamp -= dServoStepSize);
                lClamp = lCurrentTime;
            }
        }
    }
}
