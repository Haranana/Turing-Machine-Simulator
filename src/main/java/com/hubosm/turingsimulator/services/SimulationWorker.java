package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.dtos.SimulationStepDto;
import com.hubosm.turingsimulator.exceptions.SimulatorWorkerException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class SimulationWorker implements Runnable {
    private final BlockingQueue<UUID> jobQueue;
    private final List<SimulationStepDto> buffer = new ArrayList<>();
    private final SimulationRunnerImpl simulationRunner;
    private final CacheServiceImpl cacheService;
    private static final Duration TTL = Duration.ofHours(2);
    private static final int BATCH = 500;
    private static final int CACHE_MAX = 1_000;

    @PostConstruct
    void boot(){
        new Thread(this, "simulationWorker").start();
    }

    @Override
    public void run() {
        while(true) try{
            UUID jobId = jobQueue.take();
            CreateTuringMachineDto dto = (CreateTuringMachineDto) cacheService.getDefObject(jobId);
            if(dto == null) continue;
            cacheService.saveHash(jobId, "status" , "RUNNING");

            simulationRunner.run(dto, (stepId, stepDto)->{
                buffer.add(stepDto);
                if(buffer.size()>BATCH){
                    flush(jobId,buffer);
                }
            });
            flush(jobId,buffer);
            cacheService.saveAllHash(jobId, Map.of("status" , "DONE","timestamp" , Instant.now().toString()));
            cacheService.setTTL(jobId, "meta" , Duration.ofHours(2));
        } catch (Exception e){
            throw new SimulatorWorkerException(e.getMessage());
        }
    }

    private void flush(UUID id, List<SimulationStepDto> batch){
        cacheService.saveAllList(id, "steps", batch);
        cacheService.setTTL(id, "steps", Duration.ofHours(2));
        batch.clear();
    }
}
