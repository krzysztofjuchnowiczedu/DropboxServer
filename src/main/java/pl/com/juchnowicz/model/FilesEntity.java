package pl.com.juchnowicz.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FilesEntity {
    private String filename;
    private UUID fileUUID;
    private String diskID;
    private String username;
    private LocalDateTime creationTime;

    public FilesEntity(String filename, UUID fileUUID, String diskID, String username, LocalDateTime creationTime) {
        this.filename = filename;
        this.fileUUID = fileUUID;
        this.diskID = diskID;
        this.username = username;
        this.creationTime = creationTime;
    }

    public String getFilename() {
        return filename;
    }

    public UUID getFileUUID() {
        return fileUUID;
    }

    public String getDiskID() {
        return diskID;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
