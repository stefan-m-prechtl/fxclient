package de.esempe.gui.login;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.esempe.ApplicationRegistry;
import de.esempe.gui.i18n.BundleKeys;
import de.esempe.gui.i18n.BundleUtil;
import de.esempe.service.login.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class LoginDialog extends Dialog<Pair<Boolean, String>>
{
	// ### GUI elements: Controls, Layouts,...
	private TextField username;
	private PasswordField password;
	private ButtonType btnTypeOk;

	// ### view data ####
	private String loginResult;
	private final ApplicationRegistry registry;

	// ### logic ####
	@Inject
	LoginService service;

	@Inject
	public LoginDialog(final ApplicationRegistry registry)
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
		// initialize GUI behavior
		this.initBehavior();
		// initialize action handler
		this.initActionHandler();
	}

	private void createGui()
	{
		final ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(BundleUtil.BUNDLE_BASENAME, this.registry.getLocale());

		this.setTitle(BundleUtil.getString(resourceBundle, BundleKeys.login_title));
		this.setHeaderText(BundleUtil.getString(resourceBundle, BundleKeys.login_headertext));
		this.setResizable(false);

		// Buttons
		this.btnTypeOk = new ButtonType(BundleUtil.getString(resourceBundle, BundleKeys.login_ok), ButtonData.OK_DONE);
		final ButtonType btnTypeCancel = new ButtonType(BundleUtil.getString(resourceBundle, BundleKeys.login_cancel), ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(this.btnTypeOk, btnTypeCancel);
		this.getDialogPane().lookupButton(this.btnTypeOk).setDisable(true);

		// Labels, textfields
		final GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		this.username = new TextField();
		this.username.setPromptText(BundleUtil.getString(resourceBundle, BundleKeys.login_username));
		this.password = new PasswordField();
		this.password.setPromptText(BundleUtil.getString(resourceBundle, BundleKeys.login_password));

		grid.add(new Label(BundleUtil.getString(resourceBundle, BundleKeys.login_username)), 0, 0);
		grid.add(this.username, 1, 0);
		grid.add(new Label(BundleUtil.getString(resourceBundle, BundleKeys.login_password)), 0, 1);
		grid.add(this.password, 1, 1);

		this.getDialogPane().setContent(grid);

	}

	private void initActionHandler()
	{
		final Button loginButton = (Button) this.getDialogPane().lookupButton(this.btnTypeOk);
		loginButton.addEventFilter(ActionEvent.ACTION, event -> this.handleOk(event));

	}

	private void initBehavior()
	{
		final Button loginButton = (Button) this.getDialogPane().lookupButton(this.btnTypeOk);
		this.username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		this.setResultConverter(dialogButton -> {
			if (dialogButton == this.btnTypeOk)
			{
				return new Pair<Boolean, String>(Boolean.TRUE, this.loginResult);
			}
			return new Pair<Boolean, String>(Boolean.FALSE, "");
		});

		// set focus
		Platform.runLater(() -> this.username.requestFocus());

	}

	// ##### Action Handler ####
	private void handleOk(final ActionEvent event)
	{
		// Login am Server per REST-Service durchführen: Ergebnis ist ein Json-Web-Token (JWT)
		final var token = this.service.login(this.username.getText(), this.password.getText());

		// set dialog result and close dialog
		if (!token.isEmpty())
		{
			this.loginResult = token;
			this.registry.putUsername(this.username.getText());
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
