package es.firmae.demo.application;

import es.firmae.demo.core.configuration.URIConstant;
import es.firmae.demo.core.exception.AlreadyExistsException;
import es.firmae.demo.core.exception.ResourceNotFoundException;
import es.firmae.demo.domain.entity.FavoritesProfile;
import es.firmae.demo.domain.repository.FavoritesProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesProfileService {

    private static final String PROFILES = URIConstant.ENTITY_PROFILES;

    private final FavoritesProfileRepository favoritesProfileRepository;

    @Autowired
    public FavoritesProfileService(FavoritesProfileRepository favoritesProfileRepository) {
        this.favoritesProfileRepository = favoritesProfileRepository;
    }

    /**
     * Receives the list of favorite users and stores them
     *
     * @param favoritesProfile list of favorite users
     * @return
     */
    public void receiveFavoritesProfiles(FavoritesProfile favoritesProfile) {
        if (validateNickname(favoritesProfile.getNickname()))
            throw new AlreadyExistsException(PROFILES, "nickname", favoritesProfile.getNickname());
        favoritesProfileRepository.save(favoritesProfile);
    }

    public boolean validateNickname(String nickname) {
        return favoritesProfileRepository.findByNickname(nickname).isPresent();
    }

    public FavoritesProfile getFavoritesProfiles(String nickname) {
        return favoritesProfileRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResourceNotFoundException(PROFILES, "nickname", nickname));
    }
}
