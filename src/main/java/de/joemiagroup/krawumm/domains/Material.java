package de.joemiagroup.krawumm.domains;


import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table material of the database
 * <br>
 *
 * @author Marlene Bauch
 *
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material extends BaseEntity{

    private String materialName;
}
