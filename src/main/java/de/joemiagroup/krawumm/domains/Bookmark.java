package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark extends BaseEntity{
    @ManyToOne(cascade = CascadeType.PERSIST)
    private RegisteredUser registeredUser;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Experiment experiment;
}
