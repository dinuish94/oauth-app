package com.sllit.ssd.oauthsample.models;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class Email {

    private String snippet;
    private Payload payload;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (snippet != null ? !snippet.equals(email.snippet) : email.snippet != null) return false;
        return payload != null ? payload.equals(email.payload) : email.payload == null;
    }

    @Override
    public int hashCode() {
        int result = snippet != null ? snippet.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
