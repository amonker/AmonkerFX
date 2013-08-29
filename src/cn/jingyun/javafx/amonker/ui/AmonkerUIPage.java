package cn.jingyun.javafx.amonker.ui;

import javafx.scene.Node;

public class AmonkerUIPage
{
	private String title;
	private Node node;
	private double width;
	private double height;
	
	public AmonkerUIPage(String title, Node node, double width, double height)
	{
		if (node == null || width <= 1 || height <= 1)
			throw new IllegalArgumentException("Node must be not null, width and height must be >1.");
		
		this.title = (title == null ? "" : title);
		this.node = node;
		this.width = width;
		this.height = height;
	}
	
	public AmonkerUIPage(String title, Node node)
	{
		this(title, node, 750, 500);
	}
	
	public AmonkerUIPage(Node node)
	{
		this(null, node);
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = (title == null ? "" : title);
	}

	public Node getNode() 
	{
		return node;
	}

	public void setNode(Node node) 
	{
		if (node == null)
			throw new NullPointerException("The node can not be null!");
		this.node = node;
	}

	public double getWidth() 
	{
		return width;
	}

	public void setWidth(double width) 
	{
		if (width <= 1)
			throw new IllegalArgumentException("Width must be above 1.");
		
		this.width = width;
	}

	public double getHeight() 
	{
		return height;
	}

	public void setHeight(double height) 
	{
		if (height <= 1)
			throw new IllegalArgumentException("Height must be above 1.");
		
		this.height = height;
	}
	
	
}
