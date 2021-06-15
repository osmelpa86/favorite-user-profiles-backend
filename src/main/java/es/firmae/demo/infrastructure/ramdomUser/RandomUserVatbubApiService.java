package es.firmae.demo.infrastructure.ramdomUser;

import com.github.vatbub.randomusers.Generator;
import com.github.vatbub.randomusers.result.RandomUser;
import es.firmae.demo.domain.repository.FavoritesProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * This is other lib to generate random user ... activate init
 */
@Service
public class RandomUserVatbubApiService {

    public final FavoritesProfileRepository userEntityRepository;

    @Autowired
    public RandomUserVatbubApiService(FavoritesProfileRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    // @PostConstruct
    public void init() {
        generateUser();
    }

    public void generateUser() {
        int numberOfResultsToGenerate = 100;
        List<RandomUser> users = Generator.generateRandomUsers(new RandomUser.RandomUserSpec(), numberOfResultsToGenerate);
        users.stream().forEach(randomUser -> {
            System.out.println("Random user: " + randomUser.toString());
            //userEntityRepository.save(UserFactory.buildUser(randomUser));
        });
    }
}
