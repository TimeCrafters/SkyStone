package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
@Autonomous(name = "Statechallenge")
public class Statechallenge extends Engine {

    @Override
    public void setProcesses() {
       addState(new RotationState( this, 1,5000, 0.225));

        addState(new Lift(this,-1, 7300));

        addState(new LateralState(this, -1, 5500));

        addState(new RotationState(this, 1,5000,1));

        addState(new Lift(this,-1, 4000));




    }
}
