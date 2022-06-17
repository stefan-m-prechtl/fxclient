package de.esempe.service.login;

import java.net.http.HttpResponse;

import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginService extends AbstractService
{
	public LoginService()
	{
		super("http://localhost:8080/monolith/rext/usermgmt/users/login");
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
