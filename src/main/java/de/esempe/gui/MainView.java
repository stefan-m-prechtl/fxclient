package de.esempe.gui;

import java.net.URL;

import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import de.esempe.gui.project.ProjectView;
import de.esempe.gui.user.UserView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author @author Stefan Prechtl (www.esempe.de)
 */
@ApplicationScoped
public class MainView extends BaseView<BorderPane, MainPresenter>
{
	enum PERSPECTIVE_TYPE
	{
		PROJECT, USER
	}

	// ### GUI elements: Controls, Layouts,...
	@FXML
	MenuItem mnuExit, mnuProject, mnuUser;

	@Inject
	LoggerExposer logger;

	@Inject
	public MainView(final MainPresenter presenter, final ApplicationRegistry registry)
	{
		super(registry);
	}

	@Override
	@PostConstruct
	protected void initView()
	{
		// this.session = this.registry.getUserSession();

		// create GUI
		this.createGui();
		// initialize data binding
		this.initDatabinding();
		// initialize GUI behavior
		this.initBehavior();
		// initialize action handler
		this.initActionHandler();

		// Initiale Perspektive setzen
		this.changePerspective(PERSPECTIVE_TYPE.PROJECT);

	}

	private void createGui()
	{
		try
		{
			final URL fxmlUrl = this.getClass().getResource("/fxml/mainview.fxml");
			final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(this);
			this.root = fxmlLoader.load();
		}
		catch (final Exception ex)
		{
			this.logger.fatalErrorConsumer().accept(ex);
		}
	}

	private void initDatabinding()
	{
	}

	private void initBehavior()
	{
		// final boolean isAdminUser = this.session.getUser().isAdmin();
		// this.perspectiveAdmin.setDisable(!isAdminUser);

	}

	private void initActionHandler()
	{
		this.mnuExit.setOnAction(e -> this.presenter.exit());
		this.mnuUser.setOnAction(e -> this.presenter.showUserPerspective());
		this.mnuProject.setOnAction(e -> this.presenter.showProjectPerspective());

	}

	// ##### Action Handler ####
	public void changePerspective(final PERSPECTIVE_TYPE type)
	{
		switch (type)
		{
			case USER ->
			{
				final UserView users = CDI.CONTAINER.getType(UserView.class);
				this.root.setCenter(users.getRoot());
			}
			case PROJECT ->
			{
				final ProjectView projectView = CDI.CONTAINER.getType(ProjectView.class);
				this.root.setCenter(projectView.getRoot());
			}
		}
	}
}
