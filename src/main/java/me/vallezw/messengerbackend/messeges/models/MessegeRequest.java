package me.vallezw.messengerbackend.messeges.models;

public class MessegeRequest {

    private String content1; // With pub key of user1 encrpyted

    private String content2; // With pub key of user2 encrpyted

    public MessegeRequest() {

    }

    public MessegeRequest(String content1, String content2) {
        this.content1 = content1;
        this.content2 = content2;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }
}
