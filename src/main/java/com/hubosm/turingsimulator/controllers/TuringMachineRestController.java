package com.hubosm.turingsimulator.controllers;


import com.hubosm.turingsimulator.dtos.CreateTuringMachineDto;
import com.hubosm.turingsimulator.services.TuringMachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/machines")
@RequiredArgsConstructor
public class TuringMachineRestController {

    private final TuringMachineService turingMachineService;

    @PostMapping
    public ResponseEntity<Void> uploadTm(@Valid @RequestBody CreateTuringMachineDto dto){

        return null;
    }
}
