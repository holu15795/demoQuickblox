package com.example.agilsun.demoquickblox.model;

/**
 * *******************************************
 * * Created by HoLu on 05/03/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class MessageModel {
    private String id;
    private String body;
    private int send;
    private int recipient;

    public MessageModel() {
    }

    public MessageModel(String id, String body, int send, int recipient) {
        this.id = id;
        this.body = body;
        this.send = send;
        this.recipient = recipient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }
}
