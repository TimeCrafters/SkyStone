package org.timecrafters.SkyStone_2019_2020.Autonomos;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;

public class RampDrive extends Drive {

    public StateConfiguration StateConfig;
    private double Throttle;
    private boolean FirstRun;
    private float DriveDirection;
    private double Inches;
    private int Ticks;
    private double MinPower;
    private double RampFunctionDegree;

    @Override
    public void init() {

    }

    @Override
    public void exec() throws InterruptedException {
        if (FirstRun) {
            DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private double DistanceToRampedPower (int currentTick, int TargetTicks, double minPower, double functionDegree) {

        //The vertex is the halfway point, where the robot stops accelerating and  starts decelerating.

        double vertex = (double) TargetTicks / 2;

        float scalar = ((float) Math.pow((2.0 / TargetTicks), functionDegree))*((float) (minPower - 1));

        //these two outputs are reflections of each other, across the vertex.
        if (currentTick >= vertex) {
            return (scalar * (Math.pow((currentTick - vertex), functionDegree))) + 1;
        } else {
            return (scalar * (Math.pow((-currentTick + vertex), functionDegree))) + 1;
        }

    }
}
