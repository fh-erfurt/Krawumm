package de.joemiagroup.krawumm.repositories.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredUserRepository extends PagingAndSortingRepository<RegisteredUser, Long>, RegisteredUserRepositoryCustom {
    List<RegisteredUser> findAllById(Long id);


    void deleteRegisteredUserById(Long id);
}
