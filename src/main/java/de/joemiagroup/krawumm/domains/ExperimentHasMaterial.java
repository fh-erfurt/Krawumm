package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentHasMaterial extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Material material;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
