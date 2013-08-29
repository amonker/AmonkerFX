package cn.jingyun.javafx.amonker.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class FXMLBorderPane extends BorderPane 
{
	public FXMLBorderPane()
	{
		loadFXML(getClass().getSimpleName() + ".fxml");
	}
	
	public FXMLBorderPane(String fxmlName)
	{
		loadFXML(fxmlName);
	}
	
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
