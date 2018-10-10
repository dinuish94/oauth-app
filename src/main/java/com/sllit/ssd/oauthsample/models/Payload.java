package com.sllit.ssd.oauthsample.models;

import java.util.List;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class Payload {

    private List<Header> headers;

    public Payload(List<Header> headers) {
        this.headers = headers;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payload payload = (Payload) o;

        return headers != null ? headers.equals(payload.headers) : payload.headers == null;
    }

    @Override
    public int hashCode() {
        return headers != null ? headers.hashCode() : 0;
    }
}
