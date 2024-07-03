package jg.chip.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record RegisterAccountDto(
    @NotEmpty String username,
    @NotEmpty String password
) {
}
