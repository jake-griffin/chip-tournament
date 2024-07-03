package jg.chip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "matchup")
public class Matchup {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Basic(optional = false)
    @Column(
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp started;

    @Column(columnDefinition="TIMESTAMP DEFAULT NULL")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp completed;

    @ManyToOne
    @JsonIgnoreProperties(value = "locations", allowSetters = true)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JsonIgnoreProperties(value = "tournament", allowSetters = true)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToMany
    @JoinTable(
        name = "matchup_player",
        joinColumns = @JoinColumn(name = "match_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "player_id", nullable = false)
    )
    private Set<Player> players;
}
