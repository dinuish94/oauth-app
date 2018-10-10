package com.sllit.ssd.oauthsample.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class AccessTokenCache {

    private Map<String, String> userAccessTokens;
    private static volatile AccessTokenCache accessTokenCache;

    private AccessTokenCache(){
        userAccessTokens = new HashMap<>();
    }

    public static AccessTokenCache getAccessTokenCache(){
        if (accessTokenCache == null){
            synchronized (AccessTokenCache.class){
                if (accessTokenCache == null) {
                    accessTokenCache = new AccessTokenCache();
                }
            }

        }
        return accessTokenCache;
    }

    public void addAccessToken(String sessionId, String accessToken){
        userAccessTokens.put(sessionId, accessToken);
    }

    public Map getAccessTokens(){
        return userAccessTokens;
    }
}
