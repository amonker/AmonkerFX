package cn.jingyun.javafx.amonker.ui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button 
{
	private ImageView imageView;
	public ImageButton(String title)
	{
		super(title);
		imageView = new ImageView();
	}
	
	public void setImage(Image image)
	{
		imageView.setImage(image);
		if (image != null)
			setGraphic(imageView);
		else
			setGraphic(null);
	}
}
