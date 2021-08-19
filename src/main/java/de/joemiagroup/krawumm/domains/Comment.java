package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

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
