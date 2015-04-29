package controller;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Mp3Class;

public class Variables {
	public static ObservableList<String> directoryLst = FXCollections.observableArrayList();
	public static ObservableList<Mp3Class> mp3Info = FXCollections.observableArrayList();
	public static File currDirec = new File(System.getProperty("user.home") + "\\Desktop");
	public static String saveDirec = System.getProperty("user.home") + "\\Desktop"; 
	public static boolean changeIsChecked = false;
}

/* 
 * -Duplicate files cannot exist in save directory
 * -Resizing Issues
 * -Deselection Problems
 * -Fails with invalid directories (both txtfields)
 * -Accepts duplicate directories
 * 
 * ${java.home}\..\jre\lib\ext\jfxrt.jar
 */


