package com.hubosm.turingsimulator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.hubosm.turingsimulator.utils.Pair;

import java.util.*;


public class TmProgram {
    private final Map<Pair<State, Character> , Transition> indexedTransitions;
    @Getter
    private final List<Transition> transitions;

    public TmProgram(){
        this.transitions = new ArrayList<>();
        this.indexedTransitions = new HashMap<>();
    }

    public void addTransition(Transition transition){
        indexedTransitions.put(new Pair<>(transition.getCurrentState(), transition.getReadSymbol()) , transition);
        transitions.add(transition);
    }

    public Optional<Transition> getTransitionByLeftSide(State state, char readSymbol){
        return Optional.ofNullable(indexedTransitions.get(new Pair<>(state, readSymbol)));
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
