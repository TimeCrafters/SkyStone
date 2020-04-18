package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.engine.V2.CyberarmStateV2;

public class ButtonPathChooser extends CyberarmStateV2 {

    private StateConfiguration stateConfig;
    private GenericDrive StateDriveS2a;
    private GenericDrive StateDriveS2b;
    private boolean pathChosen;

    public ButtonPathChooser(StateConfiguration stateConfig) {
        this.stateConfig = stateConfig;
    }

    @Override
    public void init() {

    }

    @Override
    public void exec() {



        if (!pathChosen) {
            if (cyberarmEngine.gamepad1.x) {
                //runs S2a
                addParallelState(new GenericDrive(stateConfig, "S2a"));
                pathChosen = true;
            } else if (cyberarmEngine.gamepad1.y) {
                //runs S2b
                addParallelState(new GenericDrive(stateConfig, "S2b"));
                pathChosen = true;
            } else  if (cyberarmEngine.gamepad1.b) {
                int ticks = stateConfig.get("S2b").variable("ticks");
                addParallelState(new GenericDrive(cyberarmEngine.gamepad1.left_stick_y, ticks));
                pathChosen = true;
            }
        } else {
            setHasFinished(childrenHaveFinished());
        }


    }
}
