package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;

public class turnwithlazer extends state{
    private DcMotor leftmotor;
    private DcMotor rightmotor;
private double power;
private double distancethreshold;
    private StateConfiguration stateconfig;
    private String stateconfigID;
    private Rev2mDistanceSensor lazersensor;
private boolean frun=true;


    public turnwithlazer(Engine engine,String stateconfigID, StateConfiguration stateconfig) {
        this.engine=engine;
        this.stateconfigID = stateconfigID;
        this.stateconfig = stateconfig;
    }

    @Override
    public void init() {
        leftmotor = engine.hardwareMap.dcMotor.get("leftDrive");
        rightmotor = engine.hardwareMap.dcMotor.get("rightDrive");
        leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        power=stateconfig.get(stateconfigID).variable("power");
  distancethreshold=stateconfig.get(stateconfigID).variable("distance");
  lazersensor = engine.hardwareMap.get(Rev2mDistanceSensor.class,"ditanceleft");

    }

    @Override
    public void exec() throws InterruptedException {
if (frun){
    leftmotor.setPower(power);
    rightmotor.setPower(-power);
    frun=false;
}

if (lazersensor.getDistance(DistanceUnit.MM)<distancethreshold){
    leftmotor.setPower(0);
    rightmotor.setPower(0);
    setFinished(true);
}
    }
}



