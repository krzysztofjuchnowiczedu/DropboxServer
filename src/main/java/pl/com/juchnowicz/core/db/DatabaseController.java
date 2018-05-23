package pl.com.juchnowicz.core.db;

import pl.com.juchnowicz.model.CustomFile;
import pl.com.juchnowicz.model.FilesEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseController {

    private final static String DB_PATH = "jdbc:sqlite:database/test1.db";

    public DatabaseController() {

    }

    static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_PATH);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public void insertFile(String filename, int diskID, String userID, LocalDateTime creationDate) {
        String sql = "INSERT INTO files(FILENAME,DISK_ID,USER_ID,CREATION_DATE,FILE_UUID) VALUES(?,?,?,?,?)";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, filename);
            preparedStatement.setInt(2, diskID);
            preparedStatement.setString(3, userID);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String creationDateText = creationDate.format(formatter);
            preparedStatement.setString(4, creationDateText);
            UUID fileUUID = UUID.randomUUID();
            preparedStatement.setString(5, fileUUID.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //Hashmap<fileUUID, diskID>
    public static ArrayList<FilesEntity> getFilesForUser(String username){
        String sql = "SELECT * FROM files WHERE files.USER_ID = (?)";
        ArrayList<FilesEntity> files = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String filename = resultSet.getString("FILENAME");
                UUID fileUUID = UUID.fromString(resultSet.getString("FILE_UUID"));
                String diskID = resultSet.getString("DISK_ID");
                String userID = resultSet.getString("USER_ID");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime creationTime = LocalDateTime.parse(resultSet.getString("CREATION_DATE"), formatter);
                FilesEntity entity = new FilesEntity(filename,fileUUID,diskID,userID,creationTime);
                files.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return files;
        }
    }


}
