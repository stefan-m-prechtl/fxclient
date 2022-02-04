package de.esempe.service.project;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import de.esempe.model.project.Project;
import de.esempe.service.AbstractService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectService extends AbstractService
{
	public ProjectService()
	{
		super("http://localhost:8080/monolith/rext/projectmgmt/projects");
		final JsonbConfig config = new JsonbConfig().withAdapters(new ProjectJsonAdapter());
		this.jsonb = JsonbBuilder.create(config);

	}

	public List<Project> loadAll()
	{
		List<Project> result = new ArrayList<>();

		try
		{
			final HttpResponse<String> response = this.doGET("");
			final String content = response.body();
			result = this.jsonb.fromJson(content, new ArrayList<Project>()
			{
			}.getClass().getGenericSuperclass());
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}
}