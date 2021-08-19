package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table pictures of the database
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
public class Pictures extends BaseEntity{

    private String pictureName;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Comment comment;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
