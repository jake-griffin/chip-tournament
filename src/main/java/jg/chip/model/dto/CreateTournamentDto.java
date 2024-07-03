package jg.chip.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTournamentDto(
    @NotBlank String name
) {
}
