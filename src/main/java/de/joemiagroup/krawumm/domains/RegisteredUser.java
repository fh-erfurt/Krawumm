package de.joemiagroup.krawumm.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUser extends BaseEntity{
    private String password;
    private String email;
    private TrueFalse loggedIn;
    private String userName;
    private TrueFalse isCreator;
    private TrueFalse isAdmin;
}
