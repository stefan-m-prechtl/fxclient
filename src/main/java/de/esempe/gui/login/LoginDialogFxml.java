package de.esempe.gui.login;

import java.net.URL;

import de.esempe.ApplicationRegistry;
import de.esempe.service.login.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class LoginDialogFxml extends Dialog<Pair<Boolean, String>>
{
	@FXML
	ButtonType btnLogin;
	@FXML
	TextField txtKennung, txtPasswort;

	private DialogPane root;

	private String loginResult;
	private final ApplicationRegistry registry;

	// ### logic ####
	@Inject
	LoginService service;

	@Inject
	public LoginDialogFxml(final ApplicationRegistry registry)
	{
		super();
		this.loginResult = null;
		this.registry = registry;

	}

	@PostConstruct
	private void initDialog()
	{
		// create GUI
		this.createGui();
		this.initBehavior();
		this.initActionHandler();
	}

	private void createGui()
	{
		try
		{
			// final URL fxmlUrl = this.getClass().getResource("/fxml/login.fxml");
			final URL fxmlUrl = this.getClass().getResource("/fxml/login2.fxml");
			final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(this);
			this.root = fxmlLoader.load();
			
			this.setTitle("Login");
			root.setHeaderText("Login mit Windowskennung");
			this.setDialogPane(root);	
							
			root.lookupButton(btnLogin).setDisable(true);
			
		}
		catch (final Exception ex)
		{
			// this.logger.fatalErrorConsumer().accept(ex);
			ex.printStackTrace();
		}
	}

	private void initActionHandler()
	{
		var loginButton = (Button) root.lookupButton(btnLogin);
		loginButton.addEventFilter(ActionEvent.ACTION, event -> this.handleOk(event));	
	}

	private void initBehavior()
	{
		var loginButton = (Button) root.lookupButton(btnLogin);
		this.txtKennung.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		this.setResultConverter(dialogButton -> {
			if (dialogButton == this.btnLogin)
			{
				return new Pair<Boolean, String>(Boolean.TRUE, this.loginResult);
			}
			return new Pair<Boolean, String>(Boolean.FALSE, "");
		});

		// set focus
		Platform.runLater(() -> this.txtKennung.requestFocus());
	}

	// ##### Action Handler ####
	private void handleOk(final ActionEvent event)
	{
		// Login am Server per REST-Service durchführen: Ergebnis ist ein Json-Web-Token (JWT)
		final var token = this.service.login(this.txtKennung.getText(), this.txtPasswort.getText());

		// set dialog result and close dialog
		if (!token.isEmpty())
		{
			this.loginResult = token;
			this.registry.putUsername(this.txtKennung.getText());
		}
		else
		{
			// do nothing
			event.consume();
			this.registry.putUsername("Keine gültiger Benutzer");

			final Alert errAlert = new Alert(AlertType.ERROR);
			errAlert.setHeaderText("Ungültige Login-Daten");
			errAlert.setContentText("Login fehlerhaft - Http Code " + this.registry.getCurrentHttpCode());
			errAlert.showAndWait();
		}
	}
}
