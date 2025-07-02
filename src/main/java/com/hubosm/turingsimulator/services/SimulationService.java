package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;

import java.util.UUID;

public interface SimulationService {
    public UUID queueSimulation(CreateTuringMachineDto dto);
}
