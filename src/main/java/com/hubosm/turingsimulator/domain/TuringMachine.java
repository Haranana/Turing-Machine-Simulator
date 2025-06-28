package com.hubosm.turingsimulator.domain;

import com.hubosm.turingsimulator.exceptions.TuringMachineException;
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

    public TuringMachine(String initialState, String acceptState, String rejectState, Collection<String> program, Character separator){
        String regex    = Pattern.quote(String.valueOf(separator));
        this.states = new HashSet<>();
        this.tape = new Tape('_');
        this.program = new TmProgram();
        this.acceptState = new State(acceptState);
        this.rejectState = new State(rejectState);
        this.initialState = new State(initialState);
        states.add(this.acceptState);
        states.add(this.initialState);

        try {
            for (String trans : program) {
                //in future there may be different separator beetwen left and right side of Transition, so here regex is used for split instead of simply using separator
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
        } catch (Exception e) {
            throw new TuringMachineException("Error in loading transitions");
        }
    }

    public TuringMachine(Set<State> states, State initialState, State acceptState, TmProgram program) {
        this.states = states;
        this.initialState = initialState;
        this.acceptState = acceptState;
        this.program = program;
    }

    public Simulation run(String input){
        int maxSteps = SimulationConfig.maxSteps;
        Simulation resultSimulation = new Simulation();
        tape.placeText(input);

        State currentState = initialState;
        char readChar = tape.readHead();
        Transition currentTransition;
        while(true){

            //Find whole Transition based on current state and character read by head
            readChar = tape.readHead();
            try{
                currentTransition = program.getTransitionByLeftSide(currentState, readChar).orElseThrow();
            } catch (Exception e) {
                throw new TuringMachineException("call to non-existing transition from: " + currentState.name() + " ; " + readChar);
            }

            //Write character under head based on Transition
            tape.writeOnHead(currentTransition.getWriteSymbol());

            //Move head based on found Transition
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

            //modify current state and save simulation step
            currentState = currentTransition.getNextState();
            resultSimulation.addStep(new SimulationStep(currentState, simulationAction));

            //decide if program should be stopped
            if(currentState.equals(acceptState)){
                resultSimulation.setOutput(true);
                break;
            }else if(currentState.equals(rejectState)){
                resultSimulation.setOutput(false);
                break;
            }else if(maxSteps == resultSimulation.getSteps().size()){
                break;
            }

        }
        return resultSimulation;
    }

    public Simulation run(String input, int maxSteps){
        Simulation resultSimulation = new Simulation();
        tape.placeText(input);

        State currentState = initialState;
        char readChar;
        Transition currentTransition;
        while(true){

            //Find whole Transition based on current state and character read by head
            readChar = tape.readHead();
            try{
                currentTransition = program.getTransitionByLeftSide(currentState, readChar).orElseThrow();
            } catch (Exception e) {
                throw new TuringMachineException("call to non-existing transition from: " + currentState.name() + " ; " + readChar);
            }

            //Write character under head based on Transition
            tape.writeOnHead(currentTransition.getWriteSymbol());

            //Move head based on found Transition
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

            //modify current state and save simulation step
            currentState = currentTransition.getNextState();
            resultSimulation.addStep(new SimulationStep(currentState, simulationAction));

            //decide if program should be stopped
            if(currentState.equals(acceptState)){
                resultSimulation.setOutput(true);
                break;
            }else if(currentState.equals(rejectState)){
                resultSimulation.setOutput(false);
                break;
            }else if(maxSteps == resultSimulation.getSteps().size()){
                break;
            }

        }
        return resultSimulation;
    }
}
