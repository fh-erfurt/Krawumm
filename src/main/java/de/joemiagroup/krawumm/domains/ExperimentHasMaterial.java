package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table experiment_has_material of the database
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
public class ExperimentHasMaterial extends BaseEntity{

    @ManyToOne(cascade = CascadeType.MERGE)
    private Material material;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
