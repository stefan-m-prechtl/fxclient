package de.esempe.service.login;

import java.net.http.HttpResponse;

import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LoginService extends AbstractService
{
	@Inject
	public LoginService(final ApplicationRegistry registry, final LoggerExposer logger)
	{
		super("usermgmt/logins/login", registry, logger);
	}

	public String login(final String user, final String password)
	{
		// TODO Json aus Objekt erzeugen
		var data = "{\"user\":\"<user>\",\"passwd\":\"<passwd>\"}";
		data = data.replace("<user>", user);
		data = data.replace("<passwd>", password);

		final HttpResponse<String> response = this.doPOST("", data);
		if ((response == null) || (response.statusCode() != 200))
		{
			return "";
		}

		final String token = response.body();
		return token;

	}

}
