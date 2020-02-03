package org.cyberarm.minibot.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.minibot.states.MiniBotDriveState;

@Autonomous(name = "CyberarmEngineV2 MiniBot", group = "MINIBOT")
public class MiniBotAutonomousEngine extends CyberarmEngineV2 {
  private int straightTravel = 1000; // MM
  private int turn90DegTravel = 641; // MM

  private double straightPower = 0.4;
  private double turnPower = 0.4;

  @Override
  public void setup() {
    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, -turn90DegTravel, turnPower, turnPower));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, -turn90DegTravel, turnPower, turnPower));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, -turn90DegTravel, turnPower, turnPower));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, -turn90DegTravel, turnPower, turnPower));
  }
}
