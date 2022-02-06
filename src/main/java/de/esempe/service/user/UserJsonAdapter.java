package de.esempe.service.user;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.model.user.User;

public class UserJsonAdapter implements JsonbAdapter<User, JsonObject>
{
	@Override
	public User adaptFromJson(final JsonObject jsonObj) throws Exception
	{

		final UUID objId = UUID.fromString(jsonObj.getString("userid"));
		final String login = jsonObj.getString("userlogin");
		final User result = new User(objId, login);

		final String firstname = jsonObj.getString("firstname");
		result.setFirstname(firstname);
		final String lastname = jsonObj.getString("lastname");
		result.setLastname(lastname);

		return result;
	}

	@Override
	public JsonObject adaptToJson(final User objUser) throws Exception
	{
		// @formatter:off
      final JsonObject result = Json.createObjectBuilder()
                .add("userid", objUser.getObjId().toString())
                .add("userlogin", objUser.getLogin())
                .add("firstname", objUser.getFirstname())
                .add("lastname", objUser.getLastname())
                .build();
      // @formatter:on
		return result;
	}
}
