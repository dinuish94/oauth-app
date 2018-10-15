package com.sllit.ssd.oauthsample.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dinukshakandasamanage on 10/9/18.
 */
public class HTTPClientUtils {

    public static String executePost(String targetURL, Map params) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(targetURL);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        params.forEach((k, v) -> {
            paramList.add(new BasicNameValuePair(k.toString(), v.toString()));
        });
        httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        CloseableHttpResponse response = client.execute(httpPost);
        ResponseHandler<String> handler = new BasicResponseHandler();
        return handler.handleResponse(response);
    }

    public static String executeGetWithAuthorization(String targetURL, String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(targetURL);
        httpGet.addHeader("Authorization", "Bearer "+ accessToken);
        ResponseHandler<String> handler = new BasicResponseHandler();
        HttpResponse response = client.execute(httpGet);
        return handler.handleResponse(response);
    }
}
