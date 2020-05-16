package org.timecrafters.Summer_2020.Nathaniel.Retrace;

public class NRPAction {
    public long TimeStamp;
    public double PowerLeft;
    public double PowerRight;
    public int CurrentTickRight;
    public int CurrentTickLeft;

    public NRPAction(long timeStamp, double powerLeft, double powerRight, int currentTickLeft, int currentTickRight) {
        TimeStamp = timeStamp;
        PowerLeft = powerLeft;
        PowerRight = powerRight;
        CurrentTickRight = currentTickRight;
        CurrentTickLeft = currentTickLeft;
    }
}
