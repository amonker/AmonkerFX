package cn.jingyun.javafx.amonker.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MultiPageTableListPane extends ScrollPane implements IPagePushListener, IPagePopListener, IPageReBackToTopListener, IApplicationStatusListener, IWindowStatusListener
{
	private VBox vbox = null;
	
	public MultiPageTableListPane()
	{
		vbox = new VBox();
		
		setContent(vbox);
		this.fitToWidthProperty().setValue(true);
		vbox.fillWidthProperty().setValue(true);
		
		hbarPolicyProperty().setValue(ScrollBarPolicy.NEVER);
		vbarPolicyProperty().setValue(ScrollBarPolicy.AS_NEEDED);
		
	}
	
	public void addNode(String title, Node node)
	{
		TitledPane pane = new TitledPane(title, node);
		VBox.setVgrow(pane, Priority.ALWAYS);
		VBox.setMargin(pane, new Insets(10));
		vbox.getChildren().add(pane);
	}

	//TODO:
	@Override
	public void willPushPage(AmonkerUIApplication app)
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IPagePushListener)tp.getContent()).willPushPage(app);
		}
	}

	@Override
	public void didPushPage(AmonkerUIApplication app)
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IPagePushListener)tp.getContent()).didPushPage(app);
		}
	}

	// TODO: IPageReBackToTopListener Methods
	@Override
	public void didReBackTop(AmonkerUIApplication app, AmonkerUIPage didPopPage)
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPageReBackToTopListener)
				((IPageReBackToTopListener)tp.getContent()).didReBackTop(app, didPopPage);
		}
	}

	//TODO: IPagePopListener Methods
	@Override
	public void willPopPage(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IPagePopListener)tp.getContent()).willPopPage(app);
		}
	}

	@Override
	public void didPopPage(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IPagePopListener)tp.getContent()).didPopPage(app);
		}
	}

	//TODO IApplicationStatusListener Methods
	@Override
	public void willExitApplication(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IApplicationStatusListener)tp.getContent()).willExitApplication(app);
		}
	}

	@Override
	public void didExitApplication(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IApplicationStatusListener)tp.getContent()).didExitApplication(app);
		}
	}

	//TODO: IWindowStatusListener Methods
	@Override
	public void willMinimizeWindow(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IWindowStatusListener)tp.getContent()).willMinimizeWindow(app);
		}
	}

	@Override
	public void didMinimizeWindow(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IWindowStatusListener)tp.getContent()).didMinimizeWindow(app);
		}
	}

	@Override
	public void willMaximizeWindow(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IWindowStatusListener)tp.getContent()).willMaximizeWindow(app);
		}
	}

	@Override
	public void didMaximizeWindow(AmonkerUIApplication app) 
	{
		ObservableList<Node> list = vbox.getChildren();
		for (Node node : list) {
			TitledPane tp = (TitledPane) node;
			if (tp.getContent() instanceof IPagePushListener)
				((IWindowStatusListener)tp.getContent()).didMaximizeWindow(app);
		}
	}
}
