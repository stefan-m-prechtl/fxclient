package de.esempe.gui.user;

import java.util.List;

import de.esempe.gui.BasePresenter;
import de.esempe.model.user.User;
import de.esempe.service.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserPresenter extends BasePresenter<UserView>
{
	// Model
	List<User> userList;

	// Schnittstelle zur REST-Service
	@Inject
	UserService service;

	@Override
	protected void viewInitialized()
	{
		// Alle Projekte per REST-API laden
		this.userList = this.service.loadAll();
		// Benutzer im View anzeigen
		this.view.showList(this.userList);

	}

}
