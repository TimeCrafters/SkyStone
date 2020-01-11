package org.cyberarm.minibot.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.minibot.states.MiniBotDriveState;

@Autonomous(name = "CyberarmEngineV2 MiniBot Test", group = "MINIBOT")
public class MiniBotEngine extends CyberarmEngineV2 {
  private int straightTravel = 1000; // MM
  private int turn90DegTravel = 250; // MM

  private double straightPower = 0.1;
  private double turnPower = 0.1;

  @Override
  public void setup() {
    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, 0, turnPower, 0.0));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, 0, turnPower, 0.0));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, 0, turnPower, 0.0));

    addState(new MiniBotDriveState(straightTravel, straightTravel, straightPower, straightPower));
    addState(new MiniBotDriveState(turn90DegTravel, 0, turnPower, 0.0));
  }
}
