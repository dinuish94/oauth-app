package com.sllit.ssd.oauthsample.models;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class Message {

    private String id;
    private String threadId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        return threadId != null ? threadId.equals(message.threadId) : message.threadId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (threadId != null ? threadId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", threadId='" + threadId + '\'' +
                '}';
    }
}
