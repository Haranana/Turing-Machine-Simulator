package com.hubosm.turingsimulator.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Simulation {
    @Getter
    private List<SimulationStep> steps;

    @Setter
    @Getter
    private Boolean output;

    public void addStep(SimulationStep step){
        steps.add(step);
    }

}
