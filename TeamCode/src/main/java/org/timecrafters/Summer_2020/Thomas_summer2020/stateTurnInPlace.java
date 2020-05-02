package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.HashMap;

public class stateTurnInPlace extends State {

    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private String stateconfigID;
    private BNO055IMU IMU;
    private double power;
    private float targetD;
    private int direction;
  private float DT;
    private StateConfiguration stateconfig;
    private float CD;

    public stateTurnInPlace(Engine engine,String stateconfigID, StateConfiguration stateconfig) {
      this.engine=engine;
        this.stateconfigID = stateconfigID;
        this.stateconfig = stateconfig;
    }
    public void init() {
        leftmotor = engine.hardwareMap.dcMotor.get("leftDrive");
        rightmotor = engine.hardwareMap.dcMotor.get("rightDrive");
        leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
    power=stateconfig.get(stateconfigID).variable("power");
    targetD=stateconfig.get(stateconfigID).variable("degrees");
    direction=stateconfig.get(stateconfigID).variable("direction");
    DT=stateconfig.get(stateconfigID).variable("allowance");

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);
    }
    @Override
    public void exec() throws InterruptedException {
       CD=IMU.getAngularOrientation().firstAngle;
        leftmotor.setPower(power*direction);
        rightmotor.setPower(-power*direction);
            if (CD>targetD-DT && CD<targetD+DT){
                leftmotor.setPower(0);
                rightmotor.setPower(0);
         setFinished(true);
            }










    }
}
