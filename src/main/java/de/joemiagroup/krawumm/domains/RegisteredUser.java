package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

/**
 * This class defines the columns of the table registered_user of the database
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
public class RegisteredUser extends BaseEntity{
    private static final long serialVersionUID = -3990512965359939849L;

    private String password;
    private String email;
    private String userName;
    private TrueFalse isCreator;
    private TrueFalse isAdmin;
}
