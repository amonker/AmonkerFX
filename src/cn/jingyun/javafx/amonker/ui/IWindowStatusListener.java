package cn.jingyun.javafx.amonker.ui;

public interface IWindowStatusListener 
{
	void willMinimizeWindow(AmonkerUIApplication app);
	void didMinimizeWindow(AmonkerUIApplication app);
	void willMaximizeWindow(AmonkerUIApplication app);
	void didMaximizeWindow(AmonkerUIApplication app);
}
