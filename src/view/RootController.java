package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import controller.MainApp;

public class RootController {
	@FXML
	private Button btnTags;
	@FXML
	private Button btnPref;

	
	public void showTags(ActionEvent event){
		MainApp.showTags();		
	}

	public void showPref(ActionEvent event){
		MainApp.showPref();
	}

}
