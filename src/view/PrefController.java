package view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import controller.MainApp;
import controller.Variables;

public class PrefController {

	@FXML
	private ListView<String> directories;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnBrowse;
	@FXML
	private Button btnBrowseSave;
	@FXML
	private Button btnRemove;	
	@FXML
	private TextField txtfieldRead;
	@FXML
	private TextField txtfieldSave;


	@FXML
	private void initialize() {
		refresh();

		if(Variables.saveDirec.length() != 0 && Variables.saveDirec.lastIndexOf("\\") == Variables.saveDirec.length() -1)
			txtfieldSave.setText(Variables.saveDirec.substring(0, Variables.saveDirec.length()-1));
		else
			txtfieldSave.setText(Variables.saveDirec);
	}


	private void refresh(){
		directories.setItems(Variables.directoryLst);
	}

	public void addDirectory(ActionEvent event){
		String direc = txtfieldRead.getText();
		if(!direc.equals("") && new File(direc).isDirectory() && !Variables.directoryLst.contains(direc)){
			Variables.directoryLst.add(txtfieldRead.getText());
			refresh();
			txtfieldRead.clear();
		}		
	}

	public void removeDirectory(ActionEvent event){
		int selectedIndex = directories.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1){
			Variables.directoryLst.remove(selectedIndex);
			refresh();
		}
	}

	public void openFileDialogRead(ActionEvent event){

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select MP3 Directory");
		if(Variables.currDirec != null) directoryChooser.setInitialDirectory(Variables.currDirec);

		File selectedDirectory = directoryChooser.showDialog(MainApp.getPrimaryStage());
		if (selectedDirectory != null) {
			txtfieldRead.setText(selectedDirectory.getAbsolutePath());
			Variables.currDirec = new File(selectedDirectory.getAbsolutePath());
		}
	}

	public void openFileDialogSave(ActionEvent event){

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Save Location");
		if(Variables.currDirec != null) directoryChooser.setInitialDirectory(Variables.currDirec);

		File selectedDirectory = directoryChooser.showDialog(MainApp.getPrimaryStage());
		if (selectedDirectory != null) {
			txtfieldSave.setText(selectedDirectory.getAbsolutePath());
			Variables.saveDirec = selectedDirectory.getAbsolutePath() + "\\";
			Variables.currDirec = new File(selectedDirectory.getAbsolutePath());
		}
	}


}
