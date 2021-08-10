package de.joemiagroup.krawumm.repositories.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Map;


public interface RegisteredUserRepositoryCustom {
    List<RegisteredUser> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);
}
