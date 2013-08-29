package cn.jingyun.javafx.amonker.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class FXMLVBox extends VBox 
{
	public FXMLVBox() { loadFXML(getClass().getSimpleName() + ".fxml"); }
	public FXMLVBox(String fxmlName) { loadFXML(fxmlName); }
	
	private void loadFXML(String fxmlName)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
