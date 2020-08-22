package me.vallezw.messengerbackend.notifications.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long notificationId;

    private String receiver; // The receiver of the notifiation

    private String sender; // The sender of the notification

    private long messageId; // The message id it refers to

    public Notification(String receiver, String sender, long messageId) {
        this.receiver = receiver;
        this.sender = sender;
        this.messageId = messageId;
    }

    public Notification() {

    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
