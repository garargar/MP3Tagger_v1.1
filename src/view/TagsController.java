package view;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.Mp3Class;
import controller.Variables;

public class TagsController {

	@FXML
	private TableView<Mp3Class> mp3Table;	
	@FXML
	private TableColumn<Mp3Class, String> mp3NameCol;
	@FXML
	private TableColumn<Mp3Class, String> mp3HasTagCol;
	@FXML
	private Button refreshBtn;
	@FXML
	private Button removeTagsBtn;
	@FXML
	private Button moveMp3Btn;

	@FXML
	private CheckBox changeCheckBox; 

	@FXML
	private TextField txtStatus;
	private Timeline timeline;

	@FXML
	private void initialize() {
		mp3NameCol.setCellValueFactory(cellData -> cellData.getValue().mp3NameProperty());
		mp3HasTagCol.setCellValueFactory(cellData -> cellData.getValue().mp3HasTagProperty());
		
		ActionEvent e = null;
		refreshMp3(e);
		changeCheckBox.setSelected(Variables.changeIsChecked);
		changeMoveCopy(e);
	}

	public TagsController(){
		timeline = new Timeline(new KeyFrame(
				Duration.millis(2000),
				ae -> txtStatus.setText("")));

	}

	public void showSuccess(){
		txtStatus.setText("Success!");
		timeline.play();
	}

	public void refreshMp3(ActionEvent event) {    	
		Variables.mp3Info = FXCollections.observableArrayList();		
		for (String direc : Variables.directoryLst){
			File[] files = new File(direc).listFiles();   

			for (File file : files) {
				String extension = "";
				int i = file.getAbsolutePath().lastIndexOf('.');
				if (i > 0) {
					extension = file.getAbsolutePath().substring(i+1);
				}

				if (extension.equals("mp3"))
					Variables.mp3Info.add(new Mp3Class(file));
			}
		}    	
		mp3Table.setItems(null);
		mp3Table.setItems(Variables.mp3Info);
	}

	public void removeTags(ActionEvent event) {
		for (Mp3Class mp3 : Variables.mp3Info){
			mp3.removeTag();    			
		}
		refreshMp3(event); 
		showSuccess();
	}

	public void moveMp3(ActionEvent event) throws InterruptedException{

		if(Variables.saveDirec != null){
			for (Mp3Class mp3 : Variables.mp3Info){
				if(moveMp3Btn.getText().equals("Move"))
					mp3.moveTo(mp3.getMp3Loc(), Variables.saveDirec + mp3.mp3NameProperty().get()).delete();   
				else
					mp3.moveTo(mp3.getMp3Loc(), Variables.saveDirec + mp3.mp3NameProperty().get());
			}

			refreshMp3(event);	
			showSuccess();
		}		
	}

	public void changeMoveCopy(ActionEvent event){
		if(changeCheckBox.selectedProperty().get() == true){
			Variables.changeIsChecked = true;
			moveMp3Btn.setText("Copy");
		}
		else {
			Variables.changeIsChecked = false;
			moveMp3Btn.setText("Move");
		}			
	}

}
