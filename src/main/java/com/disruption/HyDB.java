package com.disruption;

import com.disruptionsystems.DragonLog;

import java.io.File;

public class HyDB {
    private static DataBaseManager dbman;
    private static final DragonLog logger = new DragonLog();
    public static void main(String[] args){
        createBasicLayout();
        dbman = new DataBaseManager().establishConnection("jdbc:sqlite:data/languagedb.chorus");

    }

    public static DragonLog getLogger(){
        return logger;
    }

    public static void createBasicLayout(){
        File folder = new File("data/");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
