package de.esempe.gui.project;

import java.net.URL;

import de.esempe.gui.ApplicationRegistry;
import de.esempe.gui.BaseView;
import de.esempe.gui.ProjectPresenter;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

@ApplicationScoped
public class ProjectView extends BaseView<AnchorPane, ProjectPresenter>
{
	@Inject
	public ProjectView(final ProjectPresenter presenter, final ApplicationRegistry registry)
	{
		super(presenter, registry);
	}

	@Override
	@PostConstruct
	protected void initView()
	{
		// create GUI
		this.createGui();
		// initialize data binding
		this.initDatabinding();
		// initialize GUI behavior
		this.initBehavior();
		// initialize action handler
		this.initActionHandler();

	}

	private void createGui()
	{
		try
		{
			final URL fxmlUrl = this.getClass().getResource("/fxml/projectview.fxml");
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
		// TODO Auto-generated method stub
	}

	private void initBehavior()
	{
		// TODO Auto-generated method stub

	}

	private void initActionHandler()
	{
		// TODO Auto-generated method stub
	}
}
