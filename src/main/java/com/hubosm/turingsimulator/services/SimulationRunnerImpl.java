package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.domain.TuringMachine;
import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.dtos.SimulationStepDto;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class SimulationRunnerImpl implements SimulationRunner{
    @Override
    public void run(CreateTuringMachineDto dto, BiConsumer<Integer, SimulationStepDto> onStepUpdate) {
        TuringMachine turingMachine = new TuringMachine(
                dto.getInitialState(),
                dto.getAcceptState(),
                dto.getRejectState(),
                dto.getProgram(),
                dto.getSeparator()
        );
        turingMachine.run(dto.getInput(), onStepUpdate);
    }
}
