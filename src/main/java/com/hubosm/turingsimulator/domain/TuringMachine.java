package com.hubosm.turingsimulator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TuringMachine {

    private Set<State> states;
    private State initialState;
    private State acceptState;
    private State rejectState;

    private Set<Character> inputAlphabet;
    private Set<Character> tapeAlphabet;

    private TmProgram program;
}
