package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 2894119989476983510L;

    @Id
    @GeneratedValue
    private Long id;
}
