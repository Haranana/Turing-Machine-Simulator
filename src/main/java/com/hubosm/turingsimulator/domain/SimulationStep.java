package com.hubosm.turingsimulator.domain;

import lombok.Getter;

public record SimulationStep(State state, Transition.TransitionAction action) {
}
