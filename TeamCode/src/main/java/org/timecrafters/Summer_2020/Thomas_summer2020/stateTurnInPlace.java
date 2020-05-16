package org.timecrafters.Summer_2020.Thomas_summer2020;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class stateTurnInPlace extends State {

    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private String stateconfigID;
    private BNO055IMU IMU;
    private double power;
    private float targetD;
    private int direction;
  private float alowance;
    private StateConfiguration stateconfig;
    private float curentrotation;
private boolean frun=true;
private boolean opdirection=false;
    public stateTurnInPlace(Engine engine,String stateconfigID, StateConfiguration stateconfig) {
      this.engine=engine;
        this.stateconfigID = stateconfigID;
        this.stateconfig = stateconfig;
    }
    public void init() {
        leftmotor = engine.hardwareMap.dcMotor.get("leftDrive");
        rightmotor = engine.hardwareMap.dcMotor.get("rightDrive");
        leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    power=stateconfig.get(stateconfigID).variable("power");
    targetD=stateconfig.get(stateconfigID).variable("degrees");
    direction=stateconfig.get(stateconfigID).variable("direction");
    alowance =stateconfig.get(stateconfigID).variable("allowance");

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);
        if (direction==0){
            opdirection=true;
        }

        if (targetD<0){
            targetD+=360;
        }







    }
    @Override
    public void exec() throws InterruptedException {
       curentrotation=-IMU.getAngularOrientation().firstAngle;
        Log.i("ThomasTurn", "Rotation Pre Adjustment : "+curentrotation);
int optimalirection= getturndiretion();
       if (curentrotation<0){
           curentrotation+=360;
       }






        if (!opdirection && direction==optimalirection){
            opdirection=true;

        }


        if (opdirection){
            direction=optimalirection;
        }

        leftmotor.setPower(power*direction);
        rightmotor.setPower(-power*direction);


            if (curentrotation>targetD- alowance && curentrotation<targetD+ alowance){
                leftmotor.setPower(0);
                rightmotor.setPower(0);
         setFinished(true);
            }


        Log.i("ThomasTurn", "Rotation Post Adjustment : "+curentrotation);




    }

private int getturndiretion(){
    float degreedif=targetD-curentrotation;
    if (degreedif>180 || (degreedif<0 && degreedif>-180)){
        return -1;
    } else {
        return 1;
    }

}

}
