package com.hubosm.turingsimulator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Transition {

    private State currentState;
    private char readSymbol;
    private State nextState;
    private char writeSymbol;
    private TransitionAction action;

    public enum TransitionAction{
        RIGHT,
        LEFT,
        STAY
    }
}
