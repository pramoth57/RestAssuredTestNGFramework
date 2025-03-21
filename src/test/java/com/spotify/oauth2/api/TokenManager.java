package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager
{
    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken()
    {
        try
        {
            if(access_token ==null || Instant.now().isAfter(expiry_time))
            {
                System.out.println("Renewing Token .....");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds-300);
            }
            else
            {
                System.out.println("Token Is Good to Use");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("ABORT!!! Failed to get Token");
        }
        return access_token;

    }




    private static Response renewToken()
    {
        HashMap<String, String> formParameters = new HashMap<String, String>();
        formParameters.put("client_id", ConfigLoader.getInstance().getClientId());
        formParameters.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParameters.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParameters.put("grant_type", ConfigLoader.getInstance().getGrantType());


        Response response = RestResources.postAccount(formParameters);

        if(response.statusCode() !=200)
        {
            throw new RuntimeException("ABORT !!! Renew Token Failure");
        }
        return response;



    }



}
