package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.PlayList;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResources
{
    //static String access_token = "BQBJDiNji6NimDg_G0qpQJfe9of-9wFF_Q1kGQSsE5fmFKuEpzJOVfIbGYjwt1y8H-fVhRShNC_o2X4KcMxtrJVFDliQrGZ_ZN15DtJybcp81TDTkpmt6R8djyUwwrmyMYE3MD6uIG0RftgtUCxy6_b-OzQLtVIo8VdIYqxjdUs3vCeFZ_HT1XsQFRARQ1jxhhMpJmoYVid1XDi2yOfG_vxpJdClAqYSZ7d26yGj6Cc7OtZmFILuWV153YrRKOAe9RKqgy2o0F9wFA-jUxnXoazePE1Jma_3AJr_WvD6HOIidaih";
    public static Response post(String path,String token,PlayList requestPlayList)
    {
       return given(getRequestSpec()).body(requestPlayList).auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .when().post(path)
                .then().spec(getResponseSpec()).extract().response();
    }

     public static Response get(String path,String token)
    {
        return  given(getRequestSpec()).auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .when().get(path).
                then().spec(getResponseSpec()).extract().response();


    }

    public static Response update(String path,String token, Object reqPlayList)
    {
        return  given(getRequestSpec()).body(reqPlayList).auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .when().put(path).
                then().spec(getResponseSpec()).extract().response();

    }

    public static Response postAccount(HashMap<String, String> formParameters)
    {
        return given(getAccountRequestSpec()).formParams(formParameters).
                when().post(API+TOKEN).
                then().spec(getResponseSpec()).extract().response();
    }



}
