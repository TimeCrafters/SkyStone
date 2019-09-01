package org.timecrafters.SkyStone_2019_2020.TeleOp;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    @Override
    public void init() {

        super.init();

    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

        double powerThrottle = 1 - engine.gamepad1.right_trigger;

        double leftJoystickDegrees = Math.atan (engine.gamepad1.left_stick_y/engine.gamepad1.left_stick_x);

        double FutureSetPowerLeft = powerThrottle * getForwardLeftPower(leftJoystickDegrees);


        engine.telemetry.addData("Power Throttle", powerThrottle);
        engine.telemetry.addData("Stick Degrees", leftJoystickDegrees);
        engine.telemetry.addData("Forward Left Power", FutureSetPowerLeft);

        engine.telemetry.update();
    }

}

