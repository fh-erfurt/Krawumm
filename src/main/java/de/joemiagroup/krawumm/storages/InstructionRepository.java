package de.joemiagroup.krawumm.storages;

//import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.domains.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "instrtuctions", path = "instrtuctions")
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}
