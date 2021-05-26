package de.joemiagroup.krawumm.storages;

import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.core.errors.SqlException;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisteredUserRepository extends BaseRepository<RegisteredUser>{
    public RegisteredUserRepository() {
        super(H2Controller.getManager().getEntityManager(), RegisteredUser.class);
    }
}
