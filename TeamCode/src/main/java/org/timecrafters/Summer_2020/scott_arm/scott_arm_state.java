package org.timecrafters.Summer_2020.scott_arm;
import com.qualcomm.robotcore.hardware.Servo;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_arm_state extends State {

    public scott_arm_state (Engine engine) { this.engine = engine;}

    // names in config
    private String szRotateZ = "z";
    private String szShoulder = "s";
    private String szElbow = "e";

    // pointers to hardware
    private Servo pRotateZ;
    private Servo pShoulder;
    private Servo pElbow;


    // track location
    private double dRotateZ;
    private double dShoulder;
    private double dElbow;

    @Override
    public void init() {
        // get pointers for hardware
        pRotateZ = engine.hardwareMap.servo.get(szRotateZ);
        pShoulder = engine.hardwareMap.servo.get(szShoulder);
        pElbow = engine.hardwareMap.servo.get(szElbow);

        // set initial positions
        pRotateZ.setPosition(0.5);
        pShoulder.setPosition(0.5);
        pElbow.setPosition(0.5);
        dRotateZ = pRotateZ.getPosition();

    }

    @Override
    public void exec() throws InterruptedException {
        if(engine.gamepad1.b){ // .....................right most
            pRotateZ.setPosition(1.0);
        }
        else if(engine.gamepad1.y){ // ................center
            pRotateZ.setPosition((0.5));
        }
        else if(engine.gamepad1.x){ // ................left most
            pRotateZ.setPosition((0.0));
        }
    }
}
