package org.timecrafters.temptingreality.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class miniBotDrive extends State {
    public DcMotor rightDrive,leftDrive;
    private double divisor;
    private boolean lock;
    private boolean toggle;
    private boolean toggleDivisor;
    @Override
    public void init() {
        leftDrive = engine.hardwareMap.dcMotor.get("leftDrive");
        rightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        divisor = 1;
    }

    @Override
    public void exec() throws InterruptedException {

        leftDrive.setPower(engine.gamepad1.left_stick_y/divisor);
        rightDrive.setPower(engine.gamepad1.right_stick_y/divisor);

        if (toggle == true && engine.gamepad1.left_bumper == true && engine.gamepad1.right_bumper == true){

            lock = !lock;

            toggle = false;
        }else {
            toggle = true;
        }

        if (lock == false && engine.gamepad1.a == true && divisor < 1 && toggleDivisor == true){
            divisor = divisor + 0.1;
            toggleDivisor = false;
        }else if (lock == false && engine.gamepad1.y == true && divisor > 0 && toggleDivisor == true){
            divisor = divisor - 0.1;
            toggleDivisor = false;
        }else{
            toggleDivisor = true;
        }

    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("lock",lock);
        engine.telemetry.addData("divsor",divisor);
        engine.telemetry.addData("toggle",toggle);
        engine.telemetry.addData("toggledivisor", toggleDivisor);
        engine.telemetry.addData("powerleft",leftDrive.getPower());
        engine.telemetry.addData("powerright",rightDrive.getPower());
    }
}

