package de.esempe.gui.login;

import java.io.IOException;
import java.net.URL;

import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public abstract class BaseDialog<R, P extends BaseDlgPresenter> extends Dialog<R>
{
	protected ApplicationRegistry registry;
	protected LoggerExposer logger;

	protected DialogPane root;
	protected P presenter;

	protected BaseDialog(ApplicationRegistry registry, LoggerExposer logger)
	{
		this.registry = registry;
		this.logger = logger;
	}

	/**
	 * Jeder konkrete Dialog muss diese Methode als @PostConstruct implementieren
	 */
	abstract protected void initDialog();

	public DialogPane getRoot()
	{
		return this.root;
	}

	private void loadFxmlAndSetController(final String fxmlResPath, final BaseDialog dlg) throws IOException
	{
		final URL fxmlUrl = this.getClass().getResource(fxmlResPath);
		final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
		fxmlLoader.setController(dlg);
		this.root = fxmlLoader.load();
	}

	protected void createGuiFromFxml(final String fxmlResPath, final BaseDialog dlg, String title, String headerText) throws IOException
	{
		loadFxmlAndSetController(fxmlResPath, dlg);
		this.setDialogPane(root);
		this.setTitle(title);
		this.root.setHeaderText(headerText);
	}

	public void setPresenter(final P presenter)
	{
		this.presenter = presenter;
		this.presenter.setView(this);
	}

}
