package com.sllit.ssd.oauthsample.models;

import java.util.List;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class MessageList {

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageList that = (MessageList) o;

        return messages != null ? messages.equals(that.messages) : that.messages == null;
    }

    @Override
    public int hashCode() {
        return messages != null ? messages.hashCode() : 0;
    }
}
