package de.esempe.gui;

import de.esempe.gui.MainView.PERSPECTIVE_TYPE;
import jakarta.enterprise.context.ApplicationScoped;
import javafx.application.Platform;

/**
 *
 * @author @author Stefan Prechtl (www.esempe.de)
 */
@ApplicationScoped
public class MainPresenter extends BasePresenter<MainView>
{

	@Override
	protected void viewInitialized()
	{
		// System.out.println("got event:" + e.getMsg());
	}

	void showProjectPerspective()
	{
		this.view.changePerspective(PERSPECTIVE_TYPE.PROJECT);
	}

	void showUserPerspective()
	{
		this.view.changePerspective(PERSPECTIVE_TYPE.USER);
	}

	void exit()
	{
		Platform.exit();
	}
}
