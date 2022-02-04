package de.esempe.gui.project;

import java.net.URL;

import de.esempe.gui.ApplicationRegistry;
import de.esempe.gui.BaseView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

@ApplicationScoped
public class ProjectView extends BaseView<AnchorPane, ProjectPresenter>
{
	// GUI-Control
	@FXML
	private ListView<String> lvwSearchResult;

	// View-Daten für Data-Bindung
	private final ListProperty<String> searchResultList = new SimpleListProperty<>();

	@Inject
	Event<ProjectView> viewInitialized;

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

		// Presenter über Abschluss der Initialisierung informieren
		this.viewInitialized.fireAsync(this);
	}

	private void createGui()
	{
		try
		{
			final URL fxmlUrl = this.getClass().getResource("/fxml/projectview.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(this);
			this.root = fxmlLoader.load();

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	private void initDatabinding()
	{
		this.lvwSearchResult.itemsProperty().bind(this.searchResultList);
	}

	private void initBehavior()
	{
		// TODO Auto-generated method stub

	}

	private void initActionHandler()
	{
		// TODO Auto-generated method stub
	}

	// ### Interface for Presenter ####

	public void showList(ObservableList<String> projectnames)
	{
		this.searchResultList.clear();
		this.searchResultList.set(projectnames);

	}
}
