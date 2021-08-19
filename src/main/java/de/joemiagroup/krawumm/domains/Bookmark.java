package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private RegisteredUser registeredUser;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Experiment experiment;
}
