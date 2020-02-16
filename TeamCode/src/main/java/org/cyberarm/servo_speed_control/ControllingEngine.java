package org.cyberarm.servo_speed_control;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;

@TeleOp(name = "Servo Speed Control", group = "SERVO")
public class ControllingEngine extends CyberarmEngineV2 {
    @Override
    public void setup() {
        addState(new InteractingState());
    }
}
