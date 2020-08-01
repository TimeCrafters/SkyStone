package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.cyberarm.engine.V2.CyberarmStateV2;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.Summer_2020.Minibot;

public class turnState extends CyberarmStateV2 {

    private Minibot robot;
    private String stateKey;
    private double power;
    private float degreesTarget;
    private int turnDirection;
    private float degreeAllowance;
    private float degreesCurrent;
    private float degreesStarting;
    private boolean useOptimalDirection;
    private boolean useStartingPosition;
    public turnState(Minibot robot, String stateKey) {
        this.robot = robot;
        this.stateKey = stateKey;
    }

    @Override
    public void init() {
       power=robot.stateConfiguration.get(stateKey).variable("power");
        degreesTarget=robot.stateConfiguration.get(stateKey).variable("degrees");
        turnDirection=robot.stateConfiguration.get(stateKey).variable("direction");
        degreeAllowance=robot.stateConfiguration.get(stateKey).variable("allowance");
        useOptimalDirection=turnDirection==0;
        try { useStartingPosition=robot.stateConfiguration.get(stateKey).variable("useStartPos");
        }catch (NullPointerException e){
            useStartingPosition=false;
        }


        if (degreesTarget<0){
            degreesTarget+=360;
        }
    }

    @Override
    public void start() {
        if (robot.stateConfiguration.allow(stateKey)) {
            degreesStarting=robot.imu.getAngularOrientation().firstAngle;


        }
    }

    @Override
    public void exec() {
        if (robot.stateConfiguration.allow(stateKey)) {
            degreesCurrent= robot.imu.getAngularOrientation().firstAngle;
            if (useStartingPosition){
                degreesCurrent=degreesStarting-degreesCurrent;
            }
           if (degreesCurrent<0){
               degreesCurrent+=360;
           }
            int optimalDirection=getturndiretion();
            if (!useOptimalDirection&& turnDirection==optimalDirection){
                useOptimalDirection=true;
            }
           if (useOptimalDirection){
               turnDirection=optimalDirection;
           }
           robot.motorDriveLeft.setPower(power*turnDirection);
           robot.motorDriveRight.setPower(-power*turnDirection);
           if (degreesCurrent>degreesTarget-degreeAllowance&&degreesCurrent<degreesTarget+degreeAllowance){
               robot.motorDriveRight.setPower(0);
               robot.motorDriveLeft.setPower(0);
               setHasFinished(true);
           }


        } else {
            setHasFinished(true);
        }
    }

    @Override
    public void telemetry() {


    }


    private int getturndiretion () {
        float degreedif = degreesTarget - degreesCurrent;
        if (degreedif > 180 || (degreedif < 0 && degreedif > -180)) {
            return -1;
        } else {
            return 1;
        }

    }
}
