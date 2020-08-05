package me.vallezw.messengerbackend.chats.models;

import com.sun.istack.NotNull;

public class CreateChatRequest {

    @NotNull
    private String user;

    public CreateChatRequest(String user) {
        this.user = user;
    }

    public CreateChatRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
