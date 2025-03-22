package com.jipmer.dto;


public class MessageDTO {

    private Integer id;
    private String messageType;
    private String messages;
    private String bucketUrl;
    private String mediaType;

    // Constructors
    public MessageDTO() {
    }

    public MessageDTO(Integer id, String messageType, String messages, String bucketUrl) {
        this.id = id;
        this.messageType = messageType;
        this.messages = messages;
        this.bucketUrl = bucketUrl;

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getBucketUrl() {
        return bucketUrl;
    }

    public void setBucketUrl(String bucketUrl) {
        this.bucketUrl = bucketUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}

