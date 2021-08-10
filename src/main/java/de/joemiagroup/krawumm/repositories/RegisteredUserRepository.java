package de.joemiagroup.krawumm.repositories;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "registeredUsers", path = "registeredUsers")
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

}