package jg.chip.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePlayerDto(
    @NotBlank String name
) {
}
