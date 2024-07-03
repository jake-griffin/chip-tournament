package jg.chip.model.dto;

import java.sql.Timestamp;

public record TournamentResponseDto(
    long id,
    String name,
    boolean mine,
    Timestamp created
) {
}
