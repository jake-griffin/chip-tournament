package jg.chip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "participant")
public class Participant {

    @EmbeddedId
    ParticipantKey id;

    @ManyToOne
    @MapsId("playerId")
    @JsonIgnoreProperties(value = "ratings", allowSetters = true)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @MapsId("tournamentId")
    @JsonIgnoreProperties(value = {"participants", "locations", "matchups"}, allowSetters = true)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "rating_type_id", nullable = false)
    private RatingType ratingType;

    @Column(nullable = false)
    private int rating;

    @Basic(optional = false)
    @Column(nullable = false)
    private int startingChips;

    @Basic(optional = false)
    @Column(nullable = false)
    private int currentChips;
}
