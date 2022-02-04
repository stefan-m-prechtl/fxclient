package de.esempe.service.login;

import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginService extends AbstractService
{
	public LoginService()
	{
		super("http://localhost:8080/treebackend/demo/user/login");
	}

	public String login(String user, String password)
	{
		// TODO Json aus Objekt erzeugen
		var data = "{\"user\":\"<user>\",\"passwd\":\"<passwd>\"}";
		data = data.replace("<user>", user);
		data = data.replace("<passwd>", password);

		// final Response response = this.doPOST("", data);
		// final String token = response.readEntity(String.class);
		// return token;
		return "";
	}

}
