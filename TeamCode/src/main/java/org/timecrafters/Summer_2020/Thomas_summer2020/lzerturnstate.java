package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.V2.CyberarmStateV2;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.Summer_2020.Minibot;

public class lzerturnstate extends CyberarmStateV2 {

    private Minibot robot;
    private String stateKey;
    private double power;
    private double distanceThreshold;
    private Rev2mDistanceSensor observedSensor;
    private boolean observedObject;

    public lzerturnstate(Minibot robot, String stateKey) {
        this.robot = robot;
        this.stateKey = stateKey;
    }

    @Override
    public void init() {
       power=robot.stateConfiguration.get(stateKey).variable("power");
       distanceThreshold=robot.stateConfiguration.get(stateKey).variable("distance");

        if (power>0){
            observedSensor=robot.sensorDistanceLeft;
        } else {
            observedSensor=robot.sensorDistanceRight;
        }
    }

    @Override
    public void start() {
        if (robot.stateConfiguration.allow(stateKey)) {



        }
    }

    @Override
    public void exec() {
        if (robot.stateConfiguration.allow(stateKey)) {
           robot.motorDriveRight.setPower(-power);
           robot.motorDriveLeft.setPower(power);
            boolean observingObject=(observedSensor.getDistance(DistanceUnit.CM)<=distanceThreshold);

            if (observingObject && !observedObject){
                observedObject=true;
            }
            if (!observingObject && observingObject){
                robot.motorDriveLeft.setPower(0);
                robot.motorDriveRight.setPower(0);
                setHasFinished(true);
            }

        } else {
            setHasFinished(true);
        }
    }

    @Override
    public void telemetry() {

    }
}
