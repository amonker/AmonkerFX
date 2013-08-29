package cn.jingyun.javafx.amonker.ui;

public interface IApplicationStatusListener 
{
	void willExitApplication(AmonkerUIApplication app);
	void didExitApplication(AmonkerUIApplication app);
}
