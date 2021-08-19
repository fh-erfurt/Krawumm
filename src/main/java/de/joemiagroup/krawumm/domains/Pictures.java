package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pictures extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String pictureName;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Comment comment;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
