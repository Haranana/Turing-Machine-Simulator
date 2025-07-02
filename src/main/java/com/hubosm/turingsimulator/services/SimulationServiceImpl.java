package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.dtos.SimulationStatusDto;
import com.hubosm.turingsimulator.dtos.SimulationStepDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Service
@RequiredArgsConstructor
public class SimulationServiceImpl{

    private final static Duration TTL = Duration.ofHours(2);
    private final BlockingQueue<UUID> jobQueue;
    private final CacheServiceImpl cacheService;


    public UUID queueSimulation(CreateTuringMachineDto dto){
        UUID jobId = UUID.randomUUID();

        cacheService.saveDefObject(jobId, dto);
        cacheService.saveAllHash(jobId,
                Map.of("status" , "QUEUED",
                        "timestamp",    Instant.now().toString()));
        cacheService.setTTL(jobId , "meta", TTL);
        jobQueue.add(jobId);
        return jobId;
    }

    public SimulationStatusDto getStatus(UUID jobId){
        return new SimulationStatusDto((String)cacheService.getHash(jobId,"meta","status"));
    }

    public List<SimulationStepDto> getSteps(UUID jobId, int offset, int limit){
       return cacheService.getList(jobId, "steps").stream().skip(offset).limit(limit).map(o->(SimulationStepDto)o).toList();
    }


}
