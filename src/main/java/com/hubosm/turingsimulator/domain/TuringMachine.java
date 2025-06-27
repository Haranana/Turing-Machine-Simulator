package com.hubosm.turingsimulator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class TuringMachine {

    private Set<State> states;
    private Set<Character> inputAlphabet;
    private State initialState;
    private State acceptState;
    private State rejectState;
    private Set<Character> tapeAlphabet;
    private TmProgram program;
    private Tape tape;

    public TuringMachine(String initialState, String acceptState, Collection<String> program, Character separator){
        String regex    = Pattern.quote(String.valueOf(separator));
        this.tape = new Tape('_');
        this.program = new TmProgram();
        this.acceptState = new State(acceptState);
        this.initialState = new State(initialState);
        states.add(this.acceptState);
        states.add(this.initialState);

        for(String trans : program){
            List<String> parts = List.of(trans.split(regex, -1));
            State currentState = new State(parts.get(0));
            String readSymbol = parts.get(1);
            State nextState = new State(parts.get(2));
            String writeSymbol = parts.get(3);
            String action = parts.get(4);

            states.add(currentState);
            states.add(nextState);

            this.program.addTransition(new Transition(currentState, readSymbol.charAt(0), nextState, writeSymbol.charAt(0), Transition.stringToAction(action)));
        }
    }

    public TuringMachine(Set<State> states, State initialState, State acceptState, TmProgram program) {
        this.states = states;
        this.initialState = initialState;
        this.acceptState = acceptState;
        this.program = program;
    }

    public Simulation run(String input){
        Simulation resultSimulation = new Simulation();
        tape.placeText(input);

        State currentState = initialState;
        char readChar = tape.readHead();
        while(true){

            Transition currentTransition = program.getTransitionByLeftSide(currentState, readChar).orElseThrow();
            tape.writeOnHead(currentTransition.getWriteSymbol());

            Transition.TransitionAction simulationAction = currentTransition.getAction();
            switch (currentTransition.getAction()){
                case RIGHT:
                    tape.moveHeadRight();
                    break;
                case LEFT:
                    tape.moveHeadLeft();
                    break;
                case STAY:
                    break;
                default:
                    break;
            }

            currentState = currentTransition.getNextState();
            resultSimulation.addStep(new SimulationStep(currentState, simulationAction));
            if(currentState == acceptState){
                resultSimulation.setOutput(true);
                break;
            }else if(currentState == rejectState){
                resultSimulation.setOutput(false);
                break;
            }else if(SimulationRules.maxSteps == resultSimulation.getSteps().size()){
                break;
            }
        }
        return resultSimulation;
    }
}
