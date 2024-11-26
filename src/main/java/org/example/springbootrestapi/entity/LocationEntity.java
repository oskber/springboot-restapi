package org.example.springbootrestapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "location", schema = "mydatabase")
public class LocationEntity {


    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Getter
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Getter
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Setter
    @Getter
    @ColumnDefault("1")
    @Column(name = "status")
    private Boolean status;

    @Getter
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created")
    private Instant dateCreated;

    @Getter
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Setter
    @Getter
    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Setter
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    @Setter
    @Getter
    private String address;

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }


    @Setter
    @Getter
    @Column(name = "coordinate", columnDefinition = "point not null")
    private Point<G2D> coordinate;

    public Boolean getDeleted() {
        return deleted;
    }

}
