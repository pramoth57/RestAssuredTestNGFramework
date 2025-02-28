package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationAPI.PlayListApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PLayListsTests extends  BaseTest
{

    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description")
    @Test(description = "Should be Able to create PlayList")
    public void shouldBeAbleToCreatePlaylist() {

        PlayList requestPlayList = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlayListApi.post(requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(PlayList.class), requestPlayList);

    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        PlayList requestPlayList = playlistBuilder("Updated Playlist Name", "Updated playlist description", true);
        Response response = PlayListApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(response.as(PlayList.class), requestPlayList);

    }

    @Test
    public void shouldBeAbleToUpdatePlaylist() {
        PlayList requestPlayList = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlayListApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

    }
    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlaylist() {
        PlayList requestPlayList = playlistBuilder("", generateDescription(), true);
        Response response = PlayListApi.post(requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class), StatusCode.CODE_400);


    }
    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithInvalidToken() {
        String invalidToken = "12345";
        PlayList requestPlayList = playlistBuilder(generateName(), generateDescription(), true);
        Response response = PlayListApi.post(invalidToken, requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);


    }

    @Step
    public PlayList playlistBuilder(String name, String description, boolean _public){
        return PlayList.builder().
                name(name).
                description(description).
                _public(_public).
                build();
    }

    @Step
    public void assertPlaylistEqual(PlayList responsePlaylist, PlayList requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertError(Error responseErr, StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
    }


}
