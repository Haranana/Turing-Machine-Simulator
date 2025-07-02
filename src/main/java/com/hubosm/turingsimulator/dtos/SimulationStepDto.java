package com.hubosm.turingsimulator.dtos;

public record SimulationStepDto(
        int tapeIndex,
        char action, //L,R,S
        char readChar,
        char writtenChar,
        String stateBefore,
        String stateAfter
) {}
