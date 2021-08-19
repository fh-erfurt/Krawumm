package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

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
