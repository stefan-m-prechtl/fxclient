package de.esempe.gui;

import java.util.Locale;
import java.util.Optional;

import de.esempe.ApplicationProperties;
import de.esempe.ApplicationProperties.PropertyName;
import de.esempe.ApplicationRegistry;
import de.esempe.gui.login.LoginDialogFxml;
import jakarta.inject.Inject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * JavaFX-Applikation - wird von main() aus App.java gestartet.
 * 
 * @author etienne
 *
 */
public class FxApp extends Application
{
	@Inject
	private ApplicationRegistry registry;

	public static void main(final String[] args)
	{
		launch();
	}

	@Override
	public void init() throws Exception
	{
		CDI.CONTAINER.isRunning();

		// Init client's registry
		this.registry = CDI.CONTAINER.getType(ApplicationRegistry.class);

		this.registry.putLocale(Locale.GERMAN);

		// Anwendungs-Properties aus Datei lesen und intern speichern
		final var appProperties = new ApplicationProperties();
		appProperties.readProps();
		this.registry.putApplicationProperties(appProperties);

	}

	@Override
	public void start(final Stage stage) throws Exception
	{

		/// Login-Dialog zu erst anzeigen
		final LoginDialogFxml dlg = CDI.CONTAINER.getType(LoginDialogFxml.class);
		//final LoginDialog dlg = CDI.CONTAINER.getType(LoginDialog.class);
		final Optional<Pair<Boolean, String>> result = dlg.showAndWait();

		// Bei fehlerhaften Login die Anwendung beenden
		if (Boolean.FALSE.equals(result.get().getKey()))
		{
			Platform.exit();
			return;
		}

		// Bei korrektem Login das Json-Web-Token intern speichern
		final var token = result.get().getValue();
		this.registry.putJsonWebToken(token);
	
		
		// Main-View erzeugen und anzeigen
		final MainView view = CDI.CONTAINER.getType(MainView.class);
		final Parent parent = view.getRoot();

		final var x = this.registry.getApplicationProperties().getIntegerProperty(PropertyName.DISPLAY_X);
		final var y = this.registry.getApplicationProperties().getIntegerProperty(PropertyName.DISPLAY_Y);

		final Scene scene = new Scene(parent, x, y);
		scene.getStylesheets().add("/styles/Styles.css");
		stage.setTitle("REXT-Client");
		stage.setResizable(true);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void stop() throws Exception
	{
		CDI.CONTAINER.shutdown();
	}

}
