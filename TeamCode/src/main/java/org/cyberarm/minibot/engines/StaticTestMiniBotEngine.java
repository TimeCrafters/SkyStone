package org.cyberarm.minibot.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.minibot.states.SkyStoneTensorFlowTestState;
import org.cyberarm.minibot.states.StaticTestState;

@TeleOp(name = "CyberarmEngineV2 MiniBot Test", group = "MINIBOT")
public class StaticTestMiniBotEngine extends CyberarmEngineV2 {
  @Override
  public void setup() {
    addState(new SkyStoneTensorFlowTestState());
//    addState(new StaticTestState());
  }
}
