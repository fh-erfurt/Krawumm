package de.joemiagroup.krawumm.repositories.registeredUsers;

import de.joemiagroup.krawumm.domains.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the RegisteredUserRepositoryImpl
 * <br>
 *
 * @author Johannes Otto
 *
 */

public interface RegisteredUserRepositoryCustom {
    List<RegisteredUser> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    boolean findUserByName(String userName);
    RegisteredUser findUserDataByName(String userName);
    boolean findUserByEmail(String email);
    List<Bookmark> findBookmarksOfUser(RegisteredUser user);
    List<Comment> findCommentsOfUser(RegisteredUser user);
    List<Rating> findRatingsOfUser(RegisteredUser user);
    List<Experiment> findExperimentsOfUser(RegisteredUser user);

}
