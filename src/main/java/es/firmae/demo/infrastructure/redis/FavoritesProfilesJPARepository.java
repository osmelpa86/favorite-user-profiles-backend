package es.firmae.demo.infrastructure.redis;

import es.firmae.demo.domain.entity.FavoritesProfile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesProfilesJPARepository extends PagingAndSortingRepository<FavoritesProfile, String>,
        JpaSpecificationExecutor<FavoritesProfile> {
}
