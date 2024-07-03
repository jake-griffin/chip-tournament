package jg.chip.model.dto;

public record LoginResponseDto(
    long accountId,
    String token
) {
}
