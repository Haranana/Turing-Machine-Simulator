package com.hubosm.turingsimulator.dtos;

public record SimulationStepDto(
        int tapeIndex,
        String action, //L,R,S
        String readChar,
        String writtenChar,
        String stateBefore,
        String stateAfter
) {}
