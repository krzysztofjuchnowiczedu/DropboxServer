package pl.com.juchnowicz.model;

import java.io.Serializable;

public class Message implements Serializable{

    private MessageType messageType;
    private Object messageBody;
    private String user;

    public Message(MessageType messageType, Object messageBody, String user) {
        this.messageType = messageType;
        this.messageBody = messageBody;
        this.user = user;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(Object messageBody) {
        this.messageBody = messageBody;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
