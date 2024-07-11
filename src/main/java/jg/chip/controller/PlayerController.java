package jg.chip.controller;

import jakarta.validation.Valid;
import jg.chip.adapter.PlayerAdapter;
import jg.chip.exception.ResourceNotFoundException;
import jg.chip.model.Player;
import jg.chip.model.dto.CreatePlayerDto;
import jg.chip.model.dto.PlayerResponseDto;
import jg.chip.repository.PlayerRepository;
import jg.chip.security.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
@Secured("ROLE_ADMIN")
@RequestMapping("/players")
public class PlayerController {
    private PlayerRepository playerRepository;
    private PlayerAdapter playerAdapter;
    private SecurityUtils securityUtils;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlayerResponseDto createPlayer(@Valid @RequestBody CreatePlayerDto playerDetails) {
        final Long currentPlayerId = securityUtils.getCurrentPlayerId();

        final Player player = playerRepository.save(
            Player.builder()
                .name(playerDetails.name())
                .build()
        );

        return playerAdapter.adapt(player, currentPlayerId);
    }

    @GetMapping
    public List<PlayerResponseDto> getAllPlayers() {
        final Long currentPlayerId = securityUtils.getCurrentPlayerId();

        return playerRepository.findAll()
            .stream()
            .map(player -> playerAdapter.adapt(player, currentPlayerId))
            .toList();
    }

    @GetMapping("/{id}")
    public PlayerResponseDto getPlayerById(@PathVariable Long id) {
        final Long currentPlayerId = securityUtils.getCurrentPlayerId();

        return playerAdapter.adapt(
            playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player does not exist with id: " + id)),
            currentPlayerId
        );
    }
}
