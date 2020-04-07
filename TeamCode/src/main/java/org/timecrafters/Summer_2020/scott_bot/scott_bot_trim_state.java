package org.timecrafters.Summer_2020.scott_bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_bot_trim_state extends State {
    public scott_bot_trim_state (Engine engine) { this.engine = engine;}

    // config name "scott_bot"
    private String szLeftWheel = "LeftWheel";
    private String szRightWheel = "RightWheel";

    // pointers to hardware
    private DcMotor pLeftDrive;
    private DcMotor pRightDrive;

    // how many ticks for 1 second of drive time at incrementing power levels
    private int[] aiLeftPowerToDistanceForward = new int[10];
    private int[] aiRightPowerToDistanceForward = new int[10];
    private int[] aiLeftPowerToDistanceBackward = new int[10];
    private int[] aiRightPowerToDistanceBackward = new int[10];

    private int[] aiLeftWheelForward = new int[10];
    private int[] aiRightWheeForward = new int[10];
    private int[] aiLeftWheelBackward = new int[10];
    private int[] aiRightWheelBackward = new int[10];

    // generic counter
    private int i;

    @Override
    public void init() {
        pLeftDrive = engine.hardwareMap.dcMotor.get(szLeftWheel);
        pRightDrive = engine.hardwareMap.dcMotor.get(szRightWheel);

        pLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        pRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        for (i=0; i<=9; i++){
            aiLeftWheelBackward[i]=0;
            aiLeftWheelForward[i]=0;
            aiRightWheelBackward[i]=0;
            aiRightWheeForward[i]=0;
            aiLeftPowerToDistanceBackward[i]=0;
            aiLeftPowerToDistanceForward[i]=0;
            aiRightPowerToDistanceBackward[i]=0;
            aiRightPowerToDistanceForward[i]=0;
        }
    }

    @Override
    public void exec() throws InterruptedException {

    }
}
