package de.joemiagroup.krawumm.repositories.ratings;

import de.joemiagroup.krawumm.domains.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ratings", path = "ratings")
public interface RatingRepository extends JpaRepository<Rating, Long> {
    void deleteRatingById(Long id);
}
