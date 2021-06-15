package es.firmae.demo.interfaces.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.firmae.demo.domain.entity.FavoritesProfile;
import es.firmae.demo.interfaces.model.FavoritesProfileResource;
import es.firmae.demo.interfaces.model.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FavoritesProfileListAdapter {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesProfileListAdapter.class);

    public static FavoritesProfile toDomain(FavoritesProfileResource favoritesProfileResource) {
        return FavoritesProfile.builder()
                .nickname(favoritesProfileResource.getNickname())
                .favoritesProfileList(mapToJson(favoritesProfileResource))
                .build();
    }

    public static List<UserRequest> toResource(FavoritesProfile favoritesProfile) throws JsonProcessingException {
        String favoritesProfiles = new String(favoritesProfile.getFavoritesProfileList(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        FavoritesProfileResource favoritesProfileResource = objectMapper.readValue(favoritesProfiles, FavoritesProfileResource.class);
        return favoritesProfileResource.getFavoriteProfilesUserList();
    }

    public static byte[] mapToJson(FavoritesProfileResource resource) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(resource);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info("Error convert to JSON");
        }
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
