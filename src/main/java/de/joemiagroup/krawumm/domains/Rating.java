package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table rating of the database
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

public class Rating extends BaseEntity{

    private int ratingValue;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
    @ManyToOne(cascade = CascadeType.MERGE)
    private RegisteredUser registeredUser;
}
