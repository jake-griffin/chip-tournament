package jg.chip.model.dto;

public record AccountResponseDto(
    long id,
    String username,
    Long playerId,
    boolean enabled,
    boolean mine
) {
}
