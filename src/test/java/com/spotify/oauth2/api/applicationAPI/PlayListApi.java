package com.spotify.oauth2.api.applicationAPI;

import com.spotify.oauth2.api.RestResources;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlayListApi
{
    @Step
    public static Response post(PlayList requestPlayList)
    {
        return RestResources.post(USERS+"/" + ConfigLoader.getInstance().getUser()+PLAYLISTS,getToken(),requestPlayList);

    }

    public static Response post(String token,PlayList requestPlayList)
    {
        return RestResources.post(USERS+"/" + ConfigLoader.getInstance().getUser()+PLAYLISTS,token,requestPlayList);
    }

    public static Response get(String playListId)
    {
        return RestResources.get(PLAYLISTS+"/"+playListId,getToken());

    }

    public static Response update(String playListId, PlayList reqPlayList)
    {
        return RestResources.update(PLAYLISTS+"/"+playListId,getToken(),reqPlayList);

    }



}
