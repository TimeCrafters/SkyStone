package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class AutoPlaceX extends State {

    private StateConfiguration StateConfig;

    private DcMotor CraneX;
    private Rev2mDistanceSensor LeftDistanceSensor;
    private Rev2mDistanceSensor RightDistanceSensor;
    private double PowerX;
    private double Threshhold;




    public AutoPlaceX(Engine engine, StateConfiguration stateConfiguration) {
        this.engine = engine;
        StateConfig = stateConfiguration;
    }

    @Override
    public void init() {
        //PowerX = StateConfig.get("").variable("placePowerX");

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        RightDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");
        LeftDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceLeft");
    }

    @Override
    public void exec() throws InterruptedException {
        double rightDistance = RightDistanceSensor.getDistance(DistanceUnit.MM);
        double leftDistance = LeftDistanceSensor.getDistance(DistanceUnit.MM);

        if (Math.abs(rightDistance - leftDistance) < Threshhold) {
            CraneX.setPower(0);
            setFinished(true);
            setFinished(true);
        } else if (rightDistance > leftDistance) {
            CraneX.setPower(PowerX);
        } else if (rightDistance < leftDistance) {
            CraneX.setPower(-PowerX);
        }
    }
}
