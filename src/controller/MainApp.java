package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static BorderPane root;
    private static AnchorPane tags;
    private static AnchorPane pref;
    
    
    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("MP3 Tag Editor");
        Image icon = new Image(MainApp.class.getResourceAsStream("/resources/icon_folder.png"));
        MainApp.primaryStage.getIcons().add(icon);
        
        MainApp.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	Properties pref = new Properties();
            	OutputStream output = null;
             
            	try {             
            		output = new FileOutputStream("preferences.properties");
             
            		// set the properties value
            		pref.setProperty("saveDirec", Variables.saveDirec);
            		pref.setProperty("changeCheckBoxState", Boolean.toString(Variables.changeIsChecked));
            		
            		String direcStr = "";
            		for(String direc : Variables.directoryLst){
            			direcStr += "??" + direc;
            		}
            		if(direcStr.equals(""))
            			pref.setProperty("direcLst", "");
            		else
            			pref.setProperty("direcLst", direcStr.substring(2));
            		            		
             
            		pref.store(output, null);
             
            	} catch (IOException io) {
            		io.printStackTrace();
            	} finally {
            		if (output != null) {
            			try {
            				output.close();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
            		}
             
            	}
            }});

        initAll();
        showTags();       
    }
    
    public void initAll() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Root.fxml"));
            root = (BorderPane) loader.load();
            
            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Tags.fxml"));
            tags = (AnchorPane) loader.load();
            
            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Pref.fxml"));
            pref = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showTags() {
    	root.setCenter(tags);
    }
    
    public static void showPref() {
    	root.setCenter(pref);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
    	    	
    	Properties pref = new Properties();
    	InputStream input = null;
     
    	try {     
    		input = new FileInputStream("preferences.properties");     
    		pref.load(input);
    		
    		if(pref.getProperty("saveDirec").length() != 0){
    			Variables.saveDirec = pref.getProperty("saveDirec");
    		} else{
    			Variables.saveDirec = System.getProperty("user.home") + "\\Desktop\\"; 
    		}
    		
    		
    		Variables.changeIsChecked = Boolean.valueOf(pref.getProperty("changeCheckBoxState"));
    		
    		String direcLst = pref.getProperty("direcLst");
    		if(direcLst.equals("")){
    			Variables.directoryLst = FXCollections.observableArrayList();
    		}
    		else{
    			String[] direcSplit = direcLst.split("[?]+");
        		for(String direc : direcSplit){
        			Variables.directoryLst.add(direc);
        		}
    		}    		
     
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
        launch(args);             
       
    }
}