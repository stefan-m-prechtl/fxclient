package de.esempe.service.project;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.model.project.Project;

public class ProjectJsonAdapter implements JsonbAdapter<Project, JsonObject>
{
	@Override
	public Project adaptFromJson(final JsonObject jsonObj) throws Exception
	{

		final UUID objId = UUID.fromString(jsonObj.getString("projectid"));
		final String name = jsonObj.getString("projectname");
		final Project result = new Project(objId, name);

		final String description = jsonObj.getString("description");
		result.setDescription(description);

		return result;
	}

	@Override
	public JsonObject adaptToJson(final Project objProject) throws Exception
	{
		// @formatter:off
      final JsonObject result = Json.createObjectBuilder()
                .add("projectid", objProject.getObjId().toString())
                .add("projectname", objProject.getName())
                .add("description", objProject.getDescription())
                .build();
      // @formatter:on
		return result;
	}
}
