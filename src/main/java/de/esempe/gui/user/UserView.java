package de.esempe.gui.user;

import java.net.URL;
import java.util.List;

import de.esempe.ApplicationRegistry;
import de.esempe.gui.BaseView;
import de.esempe.model.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@ApplicationScoped
public class UserView extends BaseView<AnchorPane, UserPresenter>
{
	// GUI-Control
	@FXML
	private ListView<User> lvwSearchResult;
	@FXML
	private TextField txtLogin, txtFirstname, txtLastname;

	// View-Daten für Data-Bindung
	private final ListProperty<User> searchResultList = new SimpleListProperty<>();
	private final SimpleStringProperty login = new SimpleStringProperty();
	private final SimpleStringProperty firstname = new SimpleStringProperty();

	@Inject
	Event<UserView> viewInitialized;

	@Inject
	public UserView(final UserPresenter presenter, final ApplicationRegistry registry)
	{
		super(registry);
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
		this.lvwSearchResult.itemsProperty().bind(this.searchResultList);
		this.txtLogin.textProperty().bind(this.login);
		this.txtFirstname.textProperty().bind(this.firstname);

	}

	private void initBehavior()
	{
		// TODO Auto-generated method stub
	}

	private void initActionHandler()
	{
		this.lvwSearchResult.getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
			this.login.set(newValue.getLogin());
			this.firstname.set(newValue.getFirstname());
		});
	}

	// ### Interface for Presenter ####

	void showList(final List<User> users)
	{
		final ObservableList<User> viewmodel = FXCollections.observableArrayList();
		viewmodel.addAll(users);
		this.searchResultList.clear();
		this.searchResultList.set(viewmodel);

	}
}
