package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pictures extends BaseEntity{
    private String pictureName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Comment comment;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Experiment experiment;
}
