package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.SimulationStepDto;

import java.util.List;
import java.util.UUID;

public interface SimulationWorker {
    void flush(UUID id, List<SimulationStepDto> batch);
}
