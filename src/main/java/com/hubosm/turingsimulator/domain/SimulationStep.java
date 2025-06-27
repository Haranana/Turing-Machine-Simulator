package com.hubosm.turingsimulator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SimulationStep {
    public static int stepCount = 0;
    private final int step;
    private final State state;
    private final Transition.TransitionAction action;

    public SimulationStep(State state, Transition.TransitionAction action) {
        this.state = state;
        this.action = action;
        step=stepCount+1;
        stepCount++;
    }
}
