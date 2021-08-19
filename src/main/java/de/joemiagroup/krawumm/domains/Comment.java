package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table comment of the database
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
public class Comment extends BaseEntity{

    private String text;
    @ManyToOne(cascade = CascadeType.MERGE)
    private RegisteredUser registeredUser;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
