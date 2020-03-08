package org.cyberarm.minibot.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.minibot.states.MiniBotActiveStoneFollowingState;

@TeleOp(name = "CyberarmEngineV2 MiniBot Stone Follower", group = "MINIBOT")
public class MiniBotActiveStoneFollowingEngine extends CyberarmEngineV2 {
    @Override
    public void setup() {
        addState(new MiniBotActiveStoneFollowingState());
    }
}
