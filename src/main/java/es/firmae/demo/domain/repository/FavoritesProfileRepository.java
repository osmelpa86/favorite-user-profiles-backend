package es.firmae.demo.domain.repository;

import es.firmae.demo.domain.entity.FavoritesProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface FavoritesProfileRepository {

    Page<FavoritesProfile> findAll(Pageable pageable);

    Page<FavoritesProfile> findAllBySpecs(Specification<FavoritesProfile> specs, Pageable pageable);

    void save(FavoritesProfile favoritesProfile);

    Optional<FavoritesProfile> findByNickname(String nickname);

}
