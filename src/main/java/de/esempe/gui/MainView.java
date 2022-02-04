package de.esempe.gui;

import java.net.URL;

import de.esempe.gui.project.ProjectView;
import de.esempe.model.UserSession;
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

	private UserSession session;

	// ### GUI elements: Controls, Layouts,...
	@FXML
	MenuItem mnuExit, mnuProject, mnuUser;

	@Inject
	public MainView(final MainPresenter presenter, final ApplicationRegistry registry)
	{
		super(presenter, registry);
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
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(this);
			this.root = fxmlLoader.load();

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
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
	}

	// ##### Action Handler ####

	public void changePerspective(final PERSPECTIVE_TYPE type)
	{
		switch (type)
		{
			case USER ->
			{
				// final UserView users= CDI.COMTAINTER.getType(UserView.class);
				// this.root.setLeft(users);
			}
			case PROJECT ->
			{
				final ProjectView projectView = CDI.COMTAINTER.getType(ProjectView.class);
				this.root.setCenter(projectView.getRoot());
			}
		}
	}
}
