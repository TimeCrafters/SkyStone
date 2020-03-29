package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class MinibotautoDriveState extends State {

    private DcMotor Right;
    private DcMotor Left;
    private int RightWheelDistance;
    private int LeftWheelDistance;
    private boolean FirstRun;
    private  boolean AButton;


    public MinibotautoDriveState(Engine engine,int RightWheelDistance,int LeftWheelDistance) {
        this.engine = engine;
        this.RightWheelDistance = RightWheelDistance;
        this.LeftWheelDistance = LeftWheelDistance;
    }

    @Override
    public void init() {
        Right = engine.hardwareMap.dcMotor.get("Right");
        Left = engine.hardwareMap.dcMotor.get("Left");
        Left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {

        // if (AButton)
        //    AButton = true;{

        //}
        if (FirstRun) {
            if (RightWheelDistance != 0) {
                Right.setPower(1);
            }
            if (LeftWheelDistance != 0) {
                Left.setPower(1);
            }

            FirstRun = false;
            Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }
        if (Right.getCurrentPosition() >= Distance(RightWheelDistance)) {
            Right.setPower(0);


        }
        if (Left.getCurrentPosition() >= Distance(LeftWheelDistance)) {
            Left.setPower(0);
        }
        if (Right.getPower() == 0 && Left.getPower() == 0) {

            setFinished(true);
        }
    }


        public
 int Distance(int distance){
        return (distance/319)*560;
    }
}
