package cn.jingyun.javafx.amonker.ui;

public interface IPageReBackToTopListener 
{
	//当上一个页面被pop后，处于栈顶的页面收到didReBackTop信号
	void didReBackTop(AmonkerUIApplication app, AmonkerUIPage didPopPage);
}
