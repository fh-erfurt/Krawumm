package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUser extends BaseEntity{
    private static final long serialVersionUID = -3990512965359939849L;

    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String email;
    private String userName;
    private TrueFalse isCreator;
    private TrueFalse isAdmin;
}
