package es.firmae.demo.interfaces.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritesProfileResource implements Serializable {

    private static final long serialVersionUID = -8678658817926719207L;

    @JsonProperty(value = "nickname")
    private String nickname;

    @JsonProperty(value = "favorites")
    private List<UserRequest> favoriteProfilesUserList;
}
