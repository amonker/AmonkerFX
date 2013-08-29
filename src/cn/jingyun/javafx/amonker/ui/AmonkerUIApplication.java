package cn.jingyun.javafx.amonker.ui;

import java.io.IOException;
import java.util.Stack;

import com.sun.javafx.Utils;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AmonkerUIApplication extends Application
{
	private final static int TITLE_BAR_HEIGHT = 40;
	
	private Button btnExit;
	private Button btnMinimize;
	private Button btnMaximize;
	private Button btnHome;
	private ImageButton btnFunc1;
	private ImageButton btnFunc2;
	private Label lblTitle;
	
	private Stage primaryStage;
	private Scene primarysScene;
	private BorderPane primaryPane;
	private AmonkerUIPage rootPage = new AmonkerUIPage(new BorderPane());
	private AmonkerUIPage currentPage = rootPage;
	
	private Stack<AmonkerUIPage> pagesStack = new Stack<AmonkerUIPage>();
	private boolean isMaximize = false;
	private Rectangle2D backupWindowBounds;
	
	private ImageView imgExit = new ImageView(new Image("/resources/exit.png"));
	private ImageView imgBack = new ImageView(new Image("/resources/back.png"));
	private ImageView imgMinimize = new ImageView(new Image("/resources/Minimize.png"));
	private ImageView imgMaximize = new ImageView(new Image("/resources/Maximize.png"));
	private ImageView imgOriSize = new ImageView(new Image("/resources/OriSize.png"));
	private ImageView imgHome = new ImageView(new Image("/resources/Home.png"));
	
	protected void initUI() {}
	@Override
	public void start(final Stage stage) throws Exception
	{
		primaryStage = stage;
		initUI();
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		primaryPane = new BorderPane();
		
		primaryPane.getStylesheets().add("/cn/jingyun/javafx/amonker/ui/ui1.css");
		
		btnExit = new Button("");
//		btnExit.setId("title-bar-button");
		btnExit.setPrefSize(30, 30);
		btnExit.setGraphic(imgExit);
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (pagesStack.size() > 0) {
					popPage();
					
				} else {
					AmonkerUIPage page = currentPage;
					IApplicationStatusListener listener = null;
					if (page.getNode() instanceof IApplicationStatusListener)
						listener = (IApplicationStatusListener) page.getNode();
					
					if (listener != null)
						listener.willExitApplication(AmonkerUIApplication.this);
					primaryStage.close();
				}
			}
		});
		
		btnMinimize = new Button();
		btnMinimize.setGraphic(imgMinimize);
//		btnMinimize.setId("title-bar-button");
		btnMinimize.setPrefSize(32, 32);
		btnMinimize.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event)
			{
				IWindowStatusListener listener = null;
				if (currentPage.getNode() instanceof IWindowStatusListener)
					listener = (IWindowStatusListener) currentPage.getNode();
				
				if (listener != null)
					listener.willMinimizeWindow(AmonkerUIApplication.this);
				primaryStage.setIconified(true);
				if (listener != null)
					listener.didMinimizeWindow(AmonkerUIApplication.this);
			}
		});
		
		btnMaximize = new Button();
		btnMaximize.setGraphic(imgMaximize);
//		btnMaximize.setId("title-bar-button");
		btnMaximize.setPrefSize(32, 32);
		btnMaximize.setOnAction(new EventHandler<ActionEvent>() {
			
			private void maximize() 
			{
				Stage stage = primaryStage;
				final double stageY = Utils.isMac() ? stage.getY() - 22 : stage.getY(); // TODO Workaround for RT-13980
                final Screen screen = Screen.getScreensForRectangle(stage.getX(), stageY, 1, 1).get(0); 
                Rectangle2D bounds = screen.getVisualBounds();
                if (bounds.getMinX() == stage.getX() && bounds.getMinY() == stageY &&
                        bounds.getWidth() == stage.getWidth() && bounds.getHeight() == stage.getHeight()) {
                    if (backupWindowBounds != null) {
                        stage.setX(backupWindowBounds.getMinX());
                        stage.setY(backupWindowBounds.getMinY());
                        stage.setWidth(backupWindowBounds.getWidth());
                        stage.setHeight(backupWindowBounds.getHeight());
                        isMaximize = false;
                        btnMaximize.setGraphic(imgMaximize);
                    }
                } else {
                    backupWindowBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                    final double newStageY = Utils.isMac() ? screen.getVisualBounds().getMinY() + 22 : screen.getVisualBounds().getMinY(); // TODO Workaround for RT-13980
                    stage.setX(screen.getVisualBounds().getMinX());
                    stage.setY(newStageY);
                    stage.setWidth(screen.getVisualBounds().getWidth());
                    stage.setHeight(screen.getVisualBounds().getHeight());
                    isMaximize = true;
                    btnMaximize.setGraphic(imgOriSize);
                }
			}
			
			@Override
			public void handle(ActionEvent event)
			{
				IWindowStatusListener listener = null;
				if (currentPage.getNode() instanceof IWindowStatusListener)
					listener = (IWindowStatusListener) currentPage.getNode();
				
				if (listener != null)
					listener.willMaximizeWindow(AmonkerUIApplication.this);
				maximize();
				if (listener != null)
					listener.didMaximizeWindow(AmonkerUIApplication.this);
			}
		});
		
		btnHome = new Button();
		btnHome.setGraphic(imgHome);
		btnHome.setVisible(false);
//		btnHome.setId("title-bar-button");
		btnHome.setPrefSize(32, 32);
		btnHome.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event)
			{
				toHome();
			}
		});
		
		lblTitle = new Label(rootPage.getTitle());
		lblTitle.getStyleClass().add("title-bar-title-text");
		lblTitle.setAlignment(Pos.BASELINE_RIGHT);
		ToolBar bar = new ToolBar(btnExit, btnMinimize, btnMaximize, btnHome, lblTitle);
		bar.getStyleClass().add("title-bar");
		
		//Drag Window on Desktop
		{
			final Point dragPoint = new Point();
			bar.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					dragPoint.setX(primaryStage.getX() - arg0.getScreenX());
					dragPoint.setY(primaryStage.getY() - arg0.getScreenY());
				}
				
			});
			
			bar.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					primaryStage.setX(arg0.getScreenX() + dragPoint.getX());
					primaryStage.setY(arg0.getScreenY() + dragPoint.getY());
				}
			});
		}
		
		
		btnFunc1 = new ImageButton("1");
//		btnFunc1.setId("title-bar-button");
		btnFunc1.setVisible(false);
		btnFunc1.setPrefSize(32, 32);
		btnFunc1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event)
			{
				if (currentPage == null ||
					!(currentPage.getNode() instanceof IFunction1Operator))
					return;
				
				IFunction1Operator opt = (IFunction1Operator)currentPage.getNode();
				opt.function1Clicked();
			}
		});
		
		btnFunc2 = new ImageButton("2");
//		btnFunc2.setId("title-bar-button");
		btnFunc2.setVisible(false);
		btnFunc2.setPrefSize(32, 32);
		btnFunc2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event)
			{
				if (currentPage == null ||
					!(currentPage.getNode() instanceof IFunction2Operator))
					return;
				
				IFunction2Operator opt = (IFunction2Operator)currentPage.getNode();
				opt.function2Clicked();
			}
		});
		
		ToolBar rightBar = new ToolBar(btnFunc2, btnFunc1);
		rightBar.getStyleClass().add("title-bar");
		
		HBox hbox = new HBox();
		hbox.getChildren().add(bar);
		hbox.getChildren().add(rightBar);
		HBox.setHgrow(bar, Priority.ALWAYS);
		
		primaryPane.setTop(hbox);
		setCurrentPage(rootPage);
		if (rootPage.getNode() instanceof IPagePushListener)
			((IPagePushListener)rootPage.getNode()).didPushPage(this);
//		primaryPane.setCenter(currentPage.getNode());
		primarysScene = new Scene(primaryPane, currentPage.getWidth(), currentPage.getHeight());
		primaryStage.setScene(primarysScene);
		primaryStage.show();
	}
	
	protected void setIcon(String iconFilename)
	{
		try {
			primaryStage.getIcons().add(new Image(getClass().getResource(iconFilename).openStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public AmonkerUIPage getRootPage()
	{
		return rootPage;
	}
	
	protected void setRootPage(AmonkerUIPage page)
	{
		if (page == null || page.equals(rootPage))
			return;
		rootPage = page;
	}
	
	private void setCurrentPage(AmonkerUIPage page)
	{
		if (page == null)
			throw new NullPointerException("It can not set null page");
		
		if (isMaximize) {
			backupWindowBounds = new Rectangle2D(backupWindowBounds.getMinX(), backupWindowBounds.getMinY(), page.getWidth(), page.getHeight() + TITLE_BAR_HEIGHT);
		} else {
			primaryStage.setWidth(page.getWidth());
			primaryStage.setHeight(page.getHeight() + TITLE_BAR_HEIGHT);
		}
		
		primaryPane.setCenter(page.getNode());
		String title = page.getTitle();
		title = title == null ? "" : title;
		primaryStage.setTitle(title);
		lblTitle.setText(title);
//		System.out.println("Title: " + title + ": " + page.getNode().getClass().getSimpleName());
		if (page.equals(rootPage))
			btnHome.setVisible(false);
		else
			btnHome.setVisible(true);
		
		if (page.getNode() instanceof IFunction1Operator) {
			btnFunc1.setVisible(true);
			btnFunc1.setDisable(false);
			IFunction1Operator opt = (IFunction1Operator) page.getNode();
			if (opt.getFunction1Title() != null)
				btnFunc1.setText(opt.getFunction1Title());
			else
				btnFunc1.setText("");
			if (opt.getFunction1Image() != null)
				btnFunc1.setImage(opt.getFunction1Image());
			else
				btnFunc1.setImage(null);
		} else {
			btnFunc1.setVisible(false);
			btnFunc1.setDisable(true);
		}
		
		if (page.getNode() instanceof IFunction2Operator) {
			btnFunc2.setVisible(true);
			btnFunc2.setDisable(false);
			IFunction2Operator opt = (IFunction2Operator) page.getNode();
			if (opt.getFunction2Title() != null)
				btnFunc2.setText(opt.getFunction2Title());
			else
				btnFunc2.setText("");
			if (opt.getFunction2Image() != null)
				btnFunc2.setImage(opt.getFunction2Image());
			else
				btnFunc2.setImage(null);
		} else {
			btnFunc2.setVisible(false);
			btnFunc2.setDisable(true);
		}
		
		currentPage = page;
		if (pagesStack.size() == 0)
			btnExit.setGraphic(imgExit);
		else
			btnExit.setGraphic(imgBack);
	}
	
	public boolean pushPage(AmonkerUIPage page)
	{
		boolean ret = false;
		if (page == null)
			return ret;
		
		IPagePushListener listener = null;
		if (page.getNode() instanceof IPagePushListener)
			listener = (IPagePushListener) page.getNode();
		
		if (listener != null)
			listener.willPushPage(this);
		pagesStack.push(page);
		setCurrentPage(page);
		
		if (listener != null)
			listener.didPushPage(this);
		return ret;
	}
	
	public AmonkerUIPage popPage() { return popPage(true); }
	
	private AmonkerUIPage popPage(boolean needSetPage)
	{
		AmonkerUIPage page = null;
		if (pagesStack.size() == 0)
			return page;
		page = pagesStack.peek();
		
		IPagePopListener listener = null;
		if (page.getNode() instanceof IPagePopListener)
			listener = (IPagePopListener) page.getNode();
		
		if (listener != null)
			listener.willPopPage(this);
		page = pagesStack.pop();
		if (listener != null)
			listener.didPopPage(this);
		
		if (!needSetPage)
			return page;
		
		if (pagesStack.size() == 0)
			setCurrentPage(rootPage);
		else
			setCurrentPage(pagesStack.peek());
		
		if (currentPage.getNode() instanceof IPageReBackToTopListener)
			((IPageReBackToTopListener)currentPage.getNode()).didReBackTop(this, page);
			
		return page;
	}
	
	public void toHome()
	{
		if (pagesStack.size() == 0)
			return;
		
		while (!pagesStack.empty())
			popPage(false);
		
		setCurrentPage(rootPage);
	}
}
