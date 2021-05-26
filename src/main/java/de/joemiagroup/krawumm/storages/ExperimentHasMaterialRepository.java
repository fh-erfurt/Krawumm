package de.joemiagroup.krawumm.storages;

import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.core.errors.SqlException;
import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
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
public class ExperimentHasMaterialRepository extends BaseRepository<ExperimentHasMaterial>{
    public ExperimentHasMaterialRepository() {
        super(H2Controller.getManager().getEntityManager(), ExperimentHasMaterial.class);
    }
}
