package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rating extends BaseEntity{
    private int ratingValue;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Experiment experiment;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private RegisteredUser registeredUser;
}
