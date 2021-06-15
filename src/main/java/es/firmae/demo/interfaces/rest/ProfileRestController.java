package es.firmae.demo.interfaces.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.firmae.demo.application.FavoritesProfileService;
import es.firmae.demo.core.configuration.URIConstant;
import es.firmae.demo.domain.entity.FavoritesProfile;
import es.firmae.demo.interfaces.adapter.FavoritesProfileListAdapter;
import es.firmae.demo.interfaces.model.FavoritesProfileResource;
import es.firmae.demo.interfaces.model.UserRequest;
import es.firmae.demo.interfaces.shared.SuccessfulContentHandler;
import es.firmae.demo.interfaces.shared.SwaggerDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ProfileRestController.ENTITY_API, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "favorite-user-profiles")
public class ProfileRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileRestController.class);

    static final String ENTITY_API = URIConstant.ENTITY_API + URIConstant.API_VERSION;
    private static final String URI_FAVORITES_PROFILE = URIConstant.URI_PROFILES;
    private static final String PROFILES = URIConstant.ENTITY_PROFILES;

    private final FavoritesProfileService service;
    private final SuccessfulContentHandler contentHandler;

    @GetMapping(path = URI_FAVORITES_PROFILE + "/{nickname}" + "/exist")
    @Operation(summary = SwaggerDocumentation.existNickname_op_summary,
            description = SwaggerDocumentation.existNickname_op_description,
            tags = "favorites-profile", responses =
    @ApiResponse(content = @Content(schema = @Schema(implementation = Boolean.class)),
            responseCode = "200", description = SwaggerDocumentation.existNickname_op_resp_description))
    public ResponseEntity<?> existNickname(@PathVariable String nickname) {
        logger.info("Received nickname: " + nickname);
        boolean exist = service.validateNickname(nickname);
        return ResponseEntity.ok(exist);
    }

    @PostMapping(path = URI_FAVORITES_PROFILE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = SwaggerDocumentation.receiveProfiles_op_summary, description = SwaggerDocumentation.receiveProfiles_op_description,
            tags = "ProvincialCommitteeRestController", responses =
    @ApiResponse(headers = @Header(name = "app-success", description = SwaggerDocumentation.receiveProfiles_head_description)))
    public ResponseEntity<?> receiveFavoritesProfile(@RequestBody @Valid FavoritesProfileResource request) {
        logger.info("Received favorites profile by nickname: " + request.getNickname());
        FavoritesProfile favoritesProfile = FavoritesProfileListAdapter.toDomain(request);
        service.receiveFavoritesProfiles(favoritesProfile);
        return ResponseEntity.noContent()
                .headers(SuccessfulContentHandler.createHeaders(contentHandler.successCreateAlert(PROFILES)))
                .build();
    }

    @GetMapping(path = URI_FAVORITES_PROFILE + "/{nickname}")
    @Operation(summary = SwaggerDocumentation.getProfile_op_summary, description = SwaggerDocumentation.getProfile_op_description,
            tags = "ProvincialCommCommitteeRestController", responses =
    @ApiResponse(content = @Content(schema = @Schema(implementation = FavoritesProfileResource.class)),
            responseCode = "200", description = SwaggerDocumentation.getProfile_resp_description))
    public ResponseEntity<?> getFavoritesProfile(@PathVariable String nickname) throws JsonProcessingException {
        logger.info("Get profile by nickname: " + nickname);
        FavoritesProfile favoritesProfiles = service.getFavoritesProfiles(nickname);
        List<UserRequest> response = FavoritesProfileListAdapter.toResource(favoritesProfiles);
        return ResponseEntity.ok(response);
    }
}
