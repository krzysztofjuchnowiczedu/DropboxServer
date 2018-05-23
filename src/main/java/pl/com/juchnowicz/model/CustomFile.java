package pl.com.juchnowicz.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomFile implements Serializable{
    private UUID fileUUID;
    private UUID userUUID;
    private String name;
    private LocalDateTime creationDateTime;
    private byte[] fileContent;
    private String contentType;

    public CustomFile() {
    }

    public CustomFile(UUID fileUUID, UUID userUUID, String name, LocalDateTime creationDateTime, byte[] fileContent, String contentType) {
        this.fileUUID = fileUUID;
        this.userUUID = userUUID;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.fileContent = fileContent;
        this.contentType = contentType;
    }

    public CustomFile(UUID fileUUID, UUID userUUID, String name, LocalDateTime creationDateTime) {
        this.fileUUID = fileUUID;
        this.userUUID = userUUID;
        this.name = name;
        this.creationDateTime = creationDateTime;
    }

    public UUID getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(UUID fileUUID) {
        this.fileUUID = fileUUID;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
