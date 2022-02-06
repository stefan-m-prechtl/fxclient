package de.esempe.model.user;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final UUID objId;
	private final String login;
	private String firstname;
	private String lastname;

	public User(final UUID objId, final String login)
	{
		this.objId = objId;
		this.login = login;
	}

	public String getLogin()
	{
		return this.login;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public UUID getObjId()
	{
		return this.objId;
	}

}
