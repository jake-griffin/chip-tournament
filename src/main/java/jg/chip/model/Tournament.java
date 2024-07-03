package jg.chip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "tournaments", allowSetters = true)
    @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Account createdBy;

    @Column(name = "account_id")
    private Long createdById;

    @CreationTimestamp
    @Basic(optional = false)
    @Column(
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;

    @ToString.Exclude
    @OneToMany(mappedBy="tournament")
    @JsonIgnoreProperties(value = "tournament", allowSetters = true)
    private List<Location> locations;

    @ToString.Exclude
    @OneToMany(mappedBy="tournament")
    @JsonIgnoreProperties(value = "tournament", allowSetters = true)
    private Collection<Participant> participants;
}
