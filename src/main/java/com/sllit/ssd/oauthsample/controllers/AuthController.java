package com.sllit.ssd.oauthsample.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sllit.ssd.oauthsample.constants.PropertyConstants;
import com.sllit.ssd.oauthsample.models.AccessTokenCache;
import com.sllit.ssd.oauthsample.utils.HTTPClientUtils;
import com.sllit.ssd.oauthsample.utils.PropertyLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dinukshakandasamanage on 10/9/18.
 */
@Controller("/")
public class AuthController {

    @GetMapping("/")
    public String home() {
        return "index";
    }


    /**
     * Receives the initial call
     * Redirects to google auth
     *
     * @return
     */
    @GetMapping("/auth")
    public String index() {
        String authEndpoint = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.AUTH_ENDPOINT);
        String redirectUri = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.REDIRECT_URI);
        String scope = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.APP_SCOPE);
        String clientId = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.CLIENT_ID);

        return "redirect:" + authEndpoint +
                "?redirect_uri=" + encodeURL(redirectUri) +
                "&prompt=consent" +
                "&response_type=code" +
                "&client_id=" + clientId +
                "&scope=" + encodeURL(scope) +
                "&access_type=offline";
    }

    /**
     * Redirect URI. Google OAuth redirects to this endpoint with
     * the code grant
     *
     * @param code
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("callback")
    public String callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

        String clientId = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.CLIENT_ID);
        String clientSecret = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.CLIENT_SECRET);
        String redirectUri = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.REDIRECT_URI);

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        String tokenResponse = HTTPClientUtils.executePost(PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.TOKEN_ENDPOINT), params);


        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> tokens = gson.fromJson(tokenResponse, type);
        String accessToken = tokens.get("access_token").toString();

        String session = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("sessionID", session);
        response.addCookie(sessionCookie);

        AccessTokenCache.getAccessTokenCache().addAccessToken(session, accessToken);

        return "redirect:/home";
    }

    public static String encodeURL(String s) {
        String result;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

}
