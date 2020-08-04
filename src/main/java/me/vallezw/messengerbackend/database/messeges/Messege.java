package me.vallezw.messengerbackend.database.messeges;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Messege {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id; // Auto generated ID for the messege

    private long chatId; // The ID of the chat which the message was sent in

    @NotNull
    private String content1;

    @NotNull
    private String content2;

    @NotNull
    private String sentby;

    public Messege() {

    }

    public Messege(long id, long chatId, String content1, String content2, String sentby) {
        this.id = id;
        this.chatId = chatId;
        this.content1 = content1;
        this.content2 = content2;
        this.sentby = sentby;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        content2 = content2;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

}
