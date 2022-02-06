package de.esempe.gui.user;

import java.net.URL;

import de.esempe.gui.ApplicationRegistry;
import de.esempe.gui.BaseView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

@ApplicationScoped
public class UserView extends BaseView<AnchorPane, UserPresenter>
{
	@Inject
	Event<UserView> viewInitialized;

	@Inject
	public UserView(final UserPresenter presenter, final ApplicationRegistry registry)
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

		// Presenter über Abschluss der Initialisierung informieren
		this.viewInitialized.fireAsync(this);
	}

	private void createGui()
	{
		try
		{
			final URL fxmlUrl = this.getClass().getResource("/fxml/userview.fxml");
			final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(this);
			this.root = fxmlLoader.load();
		}
		catch (final Exception e)
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
