package pl.com.juchnowicz.model;

import java.util.Objects;
import java.util.UUID;

public class ServerTask {
    private String username;
    private String diskID;
    private boolean isUpload;
    private UUID fileUUID;
    private CustomFile customFile;

    public ServerTask(String username, String diskID, boolean isUpload, UUID fileUUID, CustomFile customFile) {
        this.username = username;
        this.diskID = diskID;
        this.isUpload = isUpload;
        this.fileUUID = fileUUID;
        this.customFile = customFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerTask that = (ServerTask) o;
        return isUpload == that.isUpload &&
                Objects.equals(username, that.username) &&
                Objects.equals(diskID, that.diskID) &&
                Objects.equals(fileUUID, that.fileUUID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, diskID, isUpload, fileUUID);
    }

    public String getUsername() {
        return username;
    }

    public String getDiskID() {
        return diskID;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public UUID getFileUUID() {
        return fileUUID;
    }

    public CustomFile getCustomFile() {
        return customFile;
    }
}
