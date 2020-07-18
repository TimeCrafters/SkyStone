package org.timecrafters.Summer_2020.Nathaniel.Demo;

import org.cyberarm.engine.V2.CyberarmStateV2;
import org.timecrafters.Summer_2020.Minibot;

public class ExampleState extends CyberarmStateV2 {

    private Minibot robot;
    private String stateKey;
    private double power;
    private int targetTicks;

    public ExampleState(Minibot robot, String stateKey) {
        this.robot = robot;
        this.stateKey = stateKey;
    }

    @Override
    public void init() {
        power = robot.stateConfiguration.get(stateKey).variable("power");
        targetTicks = robot.stateConfiguration.get(stateKey).variable("ticks");
    }

    @Override
    public void start() {
        robot.motorDriveRight.setPower(power);
        robot.motorDriveLeft.setPower(power);
    }

    @Override
    public void exec() {

        setHasFinished(Math.abs(robot.motorDriveLeft.getCurrentPosition()) >= targetTicks);

    }
}
