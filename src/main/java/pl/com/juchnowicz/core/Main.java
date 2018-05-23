package pl.com.juchnowicz.core;

import pl.com.juchnowicz.core.db.DatabaseController;
import pl.com.juchnowicz.helpers.DisksFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Main {


    public static void main(String[] args) {
        //DisksFactory.getDisks(5);
        MainService mainService = new MainService();
        //MainService mainService = new MainService();
       // DatabaseController databaseController = new DatabaseController();

////        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//////
//        databaseController.insertFile("newTest2",1, "abc", LocalDateTime.now());
//        databaseController.insertFile("newTest3",2, "abc", LocalDateTime.now());
//        databaseController.insertFile("newTest22",1, "abc", LocalDateTime.now());
//        databaseController.insertFile("newTest22",2, "abc", LocalDateTime.now());

//        DatabaseController.getFilesForUser("abc");
    }

}
