package de.joemiagroup.krawumm.repositories.instructions;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public interface InstructionRepositoryCustom {
    List<Instruction> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);
    List<String> getInstructionsForExperiment (Experiment experiment);
}