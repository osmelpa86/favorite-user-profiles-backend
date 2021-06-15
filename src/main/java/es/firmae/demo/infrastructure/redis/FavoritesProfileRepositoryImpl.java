package es.firmae.demo.infrastructure.redis;

import es.firmae.demo.domain.entity.FavoritesProfile;
import es.firmae.demo.domain.repository.FavoritesProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoritesProfileRepositoryImpl implements FavoritesProfileRepository {

    private final FavoritesProfilesJPARepository impl;

    @Autowired
    public FavoritesProfileRepositoryImpl(FavoritesProfilesJPARepository impl) {
        this.impl = impl;
    }

    @Override
    public Page<FavoritesProfile> findAll(Pageable pageable) {
        return impl.findAll(pageable);
    }

    @Override
    public Page<FavoritesProfile> findAllBySpecs(Specification<FavoritesProfile> specs, Pageable pageable) {
        return impl.findAll(specs, pageable);
    }

    @Override
    public void save(FavoritesProfile favoritesProfile) {
        impl.save(favoritesProfile);
    }

    @Override
    public Optional<FavoritesProfile> findByNickname(String nickname) {
        return impl.findById(nickname);
    }
}
