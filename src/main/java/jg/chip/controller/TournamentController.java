package jg.chip.controller;

import jakarta.validation.Valid;
import jg.chip.adapter.TournamentAdapter;
import jg.chip.exception.ForbiddenException;
import jg.chip.exception.ResourceNotFoundException;
import jg.chip.model.Tournament;
import jg.chip.model.dto.CreateTournamentDto;
import jg.chip.model.dto.TournamentResponseDto;
import jg.chip.model.dto.UpdateTournamentDto;
import jg.chip.repository.TournamentRepository;
import jg.chip.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/tournaments")
public class TournamentController {
    private TournamentRepository tournamentRepository;
    private TournamentAdapter tournamentAdapter;
    private SecurityUtils securityUtils;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Secured("ROLE_ADMIN")
    public TournamentResponseDto createTournament(@Valid @RequestBody CreateTournamentDto tournamentDetails) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        final Tournament tournament = tournamentRepository.save(
            Tournament.builder()
                .name(tournamentDetails.name())
                .createdById(currentAccountId)
                .build()
        );

        return tournamentAdapter.adapt(tournament, currentAccountId);
    }

    @GetMapping
    public List<TournamentResponseDto> getTournamentsToday() {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return tournamentRepository.findAllToday()
            .stream()
            .map(tournament -> tournamentAdapter.adapt(tournament, currentAccountId))
            .toList();
    }

    @GetMapping("/archive")
    public List<TournamentResponseDto> getTournamentArchive() {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return tournamentRepository.findBeforeToday()
            .stream()
            .map(tournament -> tournamentAdapter.adapt(tournament, currentAccountId))
            .toList();
    }

    @GetMapping("/mine")
    @Secured("ROLE_ADMIN")
    public List<TournamentResponseDto> getMyTournaments() {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return tournamentRepository.findByAccountId(currentAccountId)
            .stream()
            .map(tournament -> tournamentAdapter.adapt(tournament, currentAccountId))
            .toList();
    }

    @GetMapping("/{id}")
    public TournamentResponseDto getTournamentById(@PathVariable Long id) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return tournamentAdapter.adapt(
            tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament does not exist with id: " + id)),
            currentAccountId
        );
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public TournamentResponseDto renameTournament(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTournamentDto tournamentDetails
    ) {
        final Tournament tournament = tournamentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tournament does not exist with id: " + id));

        final long currentAccountId = securityUtils.getCurrentAccountId();
        if (tournament.getCreatedById() != currentAccountId) {
            throw new ForbiddenException("Tournament does not belong to currently logged in account");
        }

        tournament.setName(tournamentDetails.name());

        return tournamentAdapter.adapt(tournamentRepository.save(tournament), currentAccountId);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTournament(@PathVariable Long id) {
        final Tournament tournament = tournamentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tournament does not exist with id: " + id));

        final long currentAccountId = securityUtils.getCurrentAccountId();
        if (tournament.getCreatedById() != currentAccountId) {
            throw new ForbiddenException("Tournament does not belong to currently logged in account");
        }

        tournamentRepository.delete(tournament);
    }
}
