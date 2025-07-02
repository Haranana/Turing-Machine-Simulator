package com.hubosm.turingsimulator.dtos;

import com.hubosm.turingsimulator.domain.State;
import com.hubosm.turingsimulator.domain.Tape;
import com.hubosm.turingsimulator.domain.TmProgram;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTuringMachineDto {
    @NotBlank
    private String initialState;
    @NotBlank
    private String acceptState;
    @NotBlank
    private String rejectState;
    private Collection<String> program;
    @NotNull
    private Character separator;
    private String input;
}
