package de.esempe.gui.login;

import de.esempe.ApplicationRegistry;
import de.esempe.service.login.LoginService;
import jakarta.inject.Inject;

public class LoginDialogPresenter extends BaseDlgPresenter<LoginDialogFxml>
{
	private ApplicationRegistry registry;

	@Inject
	public LoginDialogPresenter(final ApplicationRegistry registry)
	{
		super();
		this.registry = registry;
	}

	@Inject
	LoginService service;

	@Override
	protected void dialogInitialized()
	{
		System.out.println("Dialog wurde initialisiert!");
	}

	String executeLogin(String kennung, String passwort)
	{
		// Login am Server per REST-Service durchführen: Ergebnis ist ein Json-Web-Token (JWT)
		final var token = this.service.login(kennung, passwort);
		if (!token.isEmpty())
		{
			this.registry.putUsername(kennung);
		}
		else
		{
			this.registry.putUsername("Keine gültiger Benutzer");
		}

		return token;
	}
}
