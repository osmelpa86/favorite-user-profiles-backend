package es.firmae.demo;

import es.firmae.demo.application.FavoritesProfileService;
import es.firmae.demo.interfaces.model.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = DEFINED_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DemoApplicationTests extends AbstractTest {

    private final static String uri_receive = "http://127.0.0.1:8081/firmae/v1/profiles";
    private final static String uri_show = "http://127.0.0.1:8081/firmae/v1/profiles/";

    @Autowired
    protected FavoritesProfileService favoritesProfileService;

    @Test
    void contextLoads() {
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Test receive favorite user profiles")
    public void test01_givenFavoriteUserProfile_whenStorageInMemoryAndNotExistThisNickname_thenResponseNoContent() throws Exception {
        // Given: a Favorite User Profile with the list of favorite user profiles
        String nickname = "N-0101";
        FavoritesProfileResource profile = buildDataProfile();
        profile.setNickname(nickname);

        // When: not exist this profile by nickname
        MvcResult mvcResultExist = mockMvc.perform(MockMvcRequestBuilders
                .get(uri_show + nickname + "/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResultExist.getResponse().getStatus();
        assertEquals(200, status);

        String exist = mvcResultExist.getResponse().getContentAsString();
        assertFalse(Boolean.parseBoolean(exist));

        // Then: is successfully stored in memory
        String inputJson = super.mapToJson(profile);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(uri_receive)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    @DisplayName("Test receive favorite user profiles")
    public void test02_givenFavoriteUserProfile_whenStorageInMemoryAndExistThisNickname_thenResponseError() throws Exception {
        // Given: a Favorite User Profile with the list of favorite user profiles
        String nickname = "N-0102";
        FavoritesProfileResource profile = buildDataProfile();
        profile.setNickname(nickname);

        String inputJson = super.mapToJson(profile);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(uri_receive)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);

        // When: exist this profile by nickname
        MvcResult mvcResultExist = mockMvc.perform(MockMvcRequestBuilders
                .get(uri_show + nickname + "/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        status = mvcResultExist.getResponse().getStatus();
        assertEquals(200, status);

        String exist = mvcResultExist.getResponse().getContentAsString();
        assertTrue(Boolean.parseBoolean(exist));

        // Then: already exist profile with nickname
        String requestJson = super.mapToJson(profile);
        MvcResult mvcResultRequest = mockMvc.perform(
                MockMvcRequestBuilders.post(uri_receive)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andReturn();

        status = mvcResultRequest.getResponse().getStatus();
        assertEquals(409, status);
    }

    @Test
    @DisplayName("Test get favorite user profiles by nickname")
    public void test03_givenNickName_whenExistThisNickname_thenResponseProfile() throws Exception {
        // Given: a nickname
        String nickName = "N-0103";
        FavoritesProfileResource profile = buildDataProfile();
        profile.setNickname(nickName);

        String inputJson = super.mapToJson(profile);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(uri_receive)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);

        // When: exist this profile by nickname
        MvcResult mvcResultExist = mockMvc.perform(MockMvcRequestBuilders
                .get(uri_show + nickName+ "/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        status = mvcResultExist.getResponse().getStatus();
        assertEquals(200, status);

        String exist = mvcResultExist.getResponse().getContentAsString();
        assertTrue(Boolean.parseBoolean(exist));

        // Then: response profile by this nickname
        String requestJson = super.mapToJson(profile);
        MvcResult mvcResultRequest = mockMvc.perform(
                MockMvcRequestBuilders.get(uri_show + nickName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andReturn();

        status = mvcResultRequest.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @DisplayName("Test get favorite user profiles by nickname")
    public void test04_givenNickName_whenNotFoundThisNickname_thenResponseError() throws Exception {
        // Given: a nickname
        String nickName = "N-0104";

        // When: not exist a profile by nickname
        FavoritesProfileResource profile = buildDataProfile();
        MvcResult mvcResultExist = mockMvc.perform(MockMvcRequestBuilders
                .get(uri_show + profile.getNickname() + "/exist")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResultExist.getResponse().getStatus();
        assertEquals(200, status);

        String exist = mvcResultExist.getResponse().getContentAsString();
        assertFalse(Boolean.parseBoolean(exist));

        // Then: not found profile by this nickname
        String requestJson = super.mapToJson(profile);
        MvcResult mvcResultRequest = mockMvc.perform(
                MockMvcRequestBuilders.get(uri_show + nickName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andReturn();

        status = mvcResultRequest.getResponse().getStatus();
        assertEquals(404, status);
    }

    public FavoritesProfileResource buildDataProfile() {
        String nickname = "N1-01";
        List<UserRequest> favoriteProfilesUserList = new ArrayList<>();

        UserRequest user = UserRequest.builder()
                .gender("male")
                .name(NameRequest.builder()
                        .title("Mr")
                        .first("Cristobal")
                        .last("Gallego")
                        .build())
                .location(LocationRequest.builder()
                        .street(StreetRequest.builder()
                                .number(6981)
                                .name("Calle del Prado")
                                .build())
                        .city("La Coruña")
                        .state("Cataluña")
                        .country("Spain")
                        .postcode(76345)
                        .coordinates(CoordinatesRequest.builder()
                                .latitude("68.3792")
                                .longitude("-131.5327")
                                .build())
                        .timezone(TimezoneRequest.builder()
                                .offset("-8:00")
                                .description("Pacific Time (US & Canada)")
                                .build())
                        .build())
                .email("cristobal.gallego@example.com")
                .dob(DobRequest.builder()
                        .date("1969-02-15T08:38:02.184Z")
                        .age(52)
                        .build())
                .record(RecordRequest.builder()
                        .date("2009-09-04T10:08:41.549Z")
                        .age(12)
                        .build())
                .phone("995-942-476")
                .cell("612-416-361")
                .picture(PictureRequest.builder()
                        .largePicture("https://randomuser.me/api/portraits/men/66.jpg")
                        .mediumPicture("https://randomuser.me/api/portraits/med/men/66.jpg")
                        .thumbnailPicture("https://randomuser.me/api/portraits/thumb/men/66.jpg")
                        .build())
                .build();

        favoriteProfilesUserList.add(user);
        return FavoritesProfileResource.builder()
                .nickname(nickname)
                .favoriteProfilesUserList(favoriteProfilesUserList)
                .build();
    }
}
