package com.hubosm.turingsimulator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class TmProgram {
    List<Transition> transitions;

    public TmProgram(){
        this.transitions = new ArrayList<>();
    }

    public void addTransition(Transition transition){
        transitions.add(transition);
    }

    public Optional<Transition> getTransitionByLeftSide(State state, char readSymbol){
        return transitions.stream().filter(t -> t.getCurrentState().equals(state) && t.getReadSymbol() == readSymbol).findFirst();
    }

    @Override
    public String toString() {
        StringBuilder resultStringBuffer = new StringBuilder();
        for(Transition trans : transitions) {
            resultStringBuffer.append(trans.toString()).append(System.lineSeparator());
        }

        return resultStringBuffer.toString();
    }
}
