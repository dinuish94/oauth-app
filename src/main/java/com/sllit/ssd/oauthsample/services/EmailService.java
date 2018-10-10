package com.sllit.ssd.oauthsample.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sllit.ssd.oauthsample.constants.PropertyConstants;
import com.sllit.ssd.oauthsample.models.*;
import com.sllit.ssd.oauthsample.utils.HTTPClientUtils;
import com.sllit.ssd.oauthsample.utils.PropertyLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
@Service
public class EmailService {

    /**
     * Retrieves user info of the user
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    public UserProfile getUser(String accessToken) throws IOException {
        // https://www.googleapis.com/oauth2/v1/userinfo?alt=json
        String userProfileEndpoint = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.USER_PROFILE_ENDPOINT);
        String user = HTTPClientUtils.executeGetWithAuthorization(userProfileEndpoint, accessToken);

        Gson gson = new Gson();
        Type type = new TypeToken<UserProfile>(){}.getType();
        UserProfile userProfile = gson.fromJson(user, type);
        return userProfile;
    }

    /**
     * Retrieves all emails for user of a specified label
     *
     * @param accessToken
     * @param userId
     * @param label
     * @return
     * @throws IOException
     */
    public List<Email> getEmailsForUser(String accessToken, String userId, String label) throws IOException {
        List<Email> emails = new ArrayList<>();
        //https://www.googleapis.com/gmail/v1/users/:userId/messages?maxResults=10&labelIds=:labelId
        String messagesListEndpoint = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.MESSAGES_ENDPOINT);

        messagesListEndpoint = String.format(messagesListEndpoint, userId, label);
        String messageList = HTTPClientUtils.executeGetWithAuthorization(messagesListEndpoint, accessToken);

        Gson gson = new Gson();
        Type type = new TypeToken<MessageList>(){}.getType();
        MessageList messages = gson.fromJson(messageList, type);

        for (Message message: messages.getMessages()){
            emails.add(getEmailFromId(userId, message.getId(), accessToken));
        }
        return emails;
    }

    /**
     * Retrieve email data for each email
     *
     * @param userId
     * @param id
     * @param accessToken
     * @return
     * @throws IOException
     */
    public Email getEmailFromId(String userId, String id, String accessToken) throws IOException {
        // https://www.googleapis.com/gmail/v1/users/:userId/messages/:messageId?format=metadata
        String emailEndpoint = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.EMAIL_ENDPOINT);

        emailEndpoint = String.format(emailEndpoint, userId, id);
        String email = HTTPClientUtils.executeGetWithAuthorization(emailEndpoint, accessToken);

        Gson gson = new Gson();
        Type type = new TypeToken<Email>(){}.getType();
        return gson.fromJson(email, type);
    }

    public List<Label> getLabelsForUser(String userId, String accessToken) throws IOException {
        String labelsEndpoint = PropertyLoader.getPropertyLoaderInstance()
                .readProperty(PropertyConstants.APP_PROPERTIES_FILE, PropertyConstants.LABELS_ENDPOINT);

        labelsEndpoint = String.format(labelsEndpoint, userId);
        String labels = HTTPClientUtils.executeGetWithAuthorization(labelsEndpoint, accessToken);

        Gson gson = new Gson();
        Type type = new TypeToken<Email>(){}.getType();
        return gson.fromJson(labels, type);
    }
}
