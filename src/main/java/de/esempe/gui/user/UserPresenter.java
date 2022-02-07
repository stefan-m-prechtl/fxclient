package de.esempe.gui.user;

import java.util.List;

import de.esempe.gui.BasePresenter;
import de.esempe.gui.project.ProjectView;
import de.esempe.model.user.User;
import de.esempe.service.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserPresenter extends BasePresenter<UserView>
{
	// Model
	List<User> userList;

	// Schnittstelle zur REST-Service
	@Inject
	UserService service;

	@Inject
	protected UserPresenter(final UserView view)
	{
		super(view);
	}

	// Alle Aktionen nach der vollständigen Initialisierung des Views
	void viewInitialized(@ObservesAsync ProjectView view)
	{
		// Alle Projekte per REST-API laden
		this.userList = this.service.loadAll();
		// Benutzer im View anzeigen
		this.view.showList(this.userList);

	}

}
