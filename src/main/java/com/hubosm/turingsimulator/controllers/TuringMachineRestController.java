package com.hubosm.turingsimulator.controllers;


import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.dtos.SimulationCreatedDto;
import com.hubosm.turingsimulator.dtos.SimulationStatusDto;
import com.hubosm.turingsimulator.dtos.SimulationStepDto;
import com.hubosm.turingsimulator.services.SimulationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/simulations")
@RequiredArgsConstructor
public class TuringMachineRestController {

    private final SimulationServiceImpl simulationService;

    @PostMapping
    public ResponseEntity<SimulationCreatedDto> simulate(@Valid @RequestBody CreateTuringMachineDto dto){
        UUID jobID = simulationService.queueSimulation(dto);
        return ResponseEntity.accepted().location(URI.create("/api/simulations/"+ jobID.toString())).body(new SimulationCreatedDto(jobID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationStatusDto> getStatus(@PathVariable UUID id){
        return ResponseEntity.ok(simulationService.getStatus(id));
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<List<SimulationStepDto>> getSimulationSteps(@PathVariable UUID id,
                                                                      @RequestParam int offset,
                                                                      @RequestParam int limit){
        return ResponseEntity.ok(simulationService.getSteps(id,offset,limit));
    }
}
