package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experiment extends BaseEntity{

    private String experimentName;
    private String description;
    private IndoorOutdoor indoorOutdoor;
    @ManyToOne(cascade = CascadeType.MERGE)
    private RegisteredUser registeredUser;
    private int difficulty;
    private String video;
    private int age;
    private float duration;
    private TrueFalse isReleased;
}
