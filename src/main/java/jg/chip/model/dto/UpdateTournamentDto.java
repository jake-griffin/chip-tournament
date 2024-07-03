package jg.chip.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTournamentDto(
    @NotBlank String name
) {
}
