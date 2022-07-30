package de.esempe.gui.login;

import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import de.esempe.service.login.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class LoginDialogFxml extends BaseDialog<Pair<Boolean, String>, LoginDialogPresenter>
{
	@FXML
	ButtonType btnLogin;
	@FXML
	TextField txtKennung, txtPasswort;

	private String loginResult;

	// ### logic ####
	@Inject
	LoginService service;

	@Inject
	public LoginDialogFxml(final ApplicationRegistry registry, LoggerExposer logger)
	{
		super(registry, logger);
		this.loginResult = null;
		this.registry = registry;
	}

	@Override
	@PostConstruct
	protected void initDialog()
	{
		// create GUI
		this.createGui();
		this.initBehavior();
		this.initActionHandler();

		this.setPresenter(new LoginDialogPresenter(registry));
	}

	private void createGui()
	{
		try
		{
			this.createGuiFromFxml("/fxml/login2.fxml", this, "Login", "Login mit Windowskennung");
			root.lookupButton(btnLogin).setDisable(true);
		}
		catch (final Exception ex)
		{
			this.logger.fatalErrorConsumer().accept(ex);
		}
	}

	private void initBehavior()
	{
		BooleanBinding validKennung = txtKennung.textProperty() //
				.isNotEmpty().and(txtKennung.textProperty().length().greaterThan(3));
		BooleanBinding validPasswort = txtKennung.textProperty() //
				.isNotEmpty() //
				.and(txtPasswort.textProperty().length().greaterThan(6));

		var loginButton = (Button) root.lookupButton(btnLogin);
		loginButton.disableProperty().bind(validKennung.and(validPasswort).not());

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

	private void initActionHandler()
	{
		var loginButton = (Button) root.lookupButton(btnLogin);
		loginButton.addEventFilter(ActionEvent.ACTION, event -> this.handleLogin(event));
	}

	// ##### Action Handler ####
	private void handleLogin(final ActionEvent event)
	{
		// Login am Server per REST-Service durchführen: Ergebnis ist ein Json-Web-Token (JWT)
		final var token = this.presenter.executeLogin(this.txtKennung.getText(), this.txtPasswort.getText());

		// set dialog result and close dialog
		if (!token.isEmpty())
		{
			this.loginResult = token;
		}
		else
		{
			// do nothing
			event.consume();

			final Alert errAlert = new Alert(AlertType.ERROR);
			errAlert.setHeaderText("Ungültige Login-Daten");
			errAlert.setContentText("Login fehlerhaft - Http Code " + this.registry.getCurrentHttpCode());
			errAlert.showAndWait();
		}
	}
}
