package de.esempe.service.user;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import de.esempe.model.user.User;
import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService extends AbstractService
{
	@Inject
	public UserService(final ApplicationRegistry registry, final LoggerExposer logger)
	{
		super("usermgmt/users", registry, logger);
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