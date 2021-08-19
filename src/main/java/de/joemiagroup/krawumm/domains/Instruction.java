package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instruction extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
