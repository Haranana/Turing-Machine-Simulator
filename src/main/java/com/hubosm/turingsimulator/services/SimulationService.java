package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.dtos.SimulationStatusDto;
import com.hubosm.turingsimulator.dtos.SimulationStepDto;

import java.util.List;
import java.util.UUID;

public interface SimulationService {
    UUID queueSimulation(CreateTuringMachineDto dto);
    SimulationStatusDto getStatus(UUID jobId);
    List<SimulationStepDto> getSteps(UUID jobId, int offset, int limit);
}
