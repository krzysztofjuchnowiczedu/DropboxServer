package pl.com.juchnowicz.helpers;

import pl.com.juchnowicz.model.Disk;

import java.io.File;
import java.util.ArrayList;

public class DisksFactory {

    public final static String DISK_FOLDER = "disks/";

    public static ArrayList<Disk> getDisks(int numberOfDisks){
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = 1; i <= numberOfDisks; i++) {
            File diskFolder = new File(DISK_FOLDER + String.valueOf(i));
            String path = diskFolder.getAbsolutePath();
            if(!diskFolder.exists()){
                diskFolder.mkdirs();
            }
            disks.add(new Disk(i));
        }

        return disks;
    }
}
