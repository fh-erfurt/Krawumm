package de.joemiagroup.krawumm.storages;

import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.core.errors.SqlException;
import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.domains.Material;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "materials", path = "materials")
public interface MaterialRepository extends JpaRepository<Material, Long> {

}
