package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rating extends BaseEntity{
    private int value;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Experiment experiment;
}
