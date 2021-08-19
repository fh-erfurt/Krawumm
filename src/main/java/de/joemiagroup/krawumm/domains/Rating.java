package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rating extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private int ratingValue;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
    @ManyToOne(cascade = CascadeType.MERGE)
    private RegisteredUser registeredUser;
}
