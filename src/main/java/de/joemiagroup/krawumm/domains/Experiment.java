package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table experiment of the database
 * <br>
 *
 * @author Marlene Bauch
 *
 */

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
