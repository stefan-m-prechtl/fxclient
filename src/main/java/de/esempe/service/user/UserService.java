package de.esempe.service.user;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import de.esempe.model.user.User;
import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService extends AbstractService
{
	public UserService()
	{
		super("http://localhost:8080/monolith/rext/usermgmt/users");
		final JsonbConfig config = new JsonbConfig().withAdapters(new UserJsonAdapter());
		this.jsonb = JsonbBuilder.create(config);
	}

	public List<User> loadAll()
	{
		List<User> result = new ArrayList<>();
		try
		{
			final HttpResponse<String> response = this.doGET("");
			final String content = response.body();
			result = this.jsonb.fromJson(content, new ArrayList<User>()
			{
			}.getClass().getGenericSuperclass());
		}
		catch (final Exception e)
		{
			System.out.println(e);
		}
		return result;
	}
}