package com.hubosm.turingsimulator.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Simulation {
    private final List<SimulationStep> steps;

    @Setter
    private Boolean output;

    public Simulation() {
        this.steps = new ArrayList<>();
    }

    public void addStep(SimulationStep step){
        this.steps.add(step);
    }



}
