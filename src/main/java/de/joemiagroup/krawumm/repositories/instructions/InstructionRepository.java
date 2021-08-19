package de.joemiagroup.krawumm.repositories.instructions;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * This interface declares methods for the InstructionRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

@RepositoryRestResource(collectionResourceRel = "instrtuctions", path = "instrtuctions")
public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    void deleteInstructionById(Long id);
    List<String> getInstructionsForExperiment (Experiment experiment);
    List<Instruction> getAllInstructions();

}
