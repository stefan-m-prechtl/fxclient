package de.esempe.model.user;

import java.io.Serializable;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final UUID objId;
	private final String _login;
	private StringProperty login;
	private String _firstname;
	private StringProperty firstname;
	private String lastname;

	public static User EmptyUser()
	{
		return new User();
	}

	private User()
	{
		this.objId = UUID.randomUUID();
		this._login = "";
		this._firstname = "";
		this.lastname = "";
	}

	public User(final UUID objId, final String login)
	{
		this.objId = objId;
		this._login = login;
	}

	public String getLogin()
	{
		if (null == this.login)
		{
			return this._login;
		}
		else
		{
			return this.login.getValue();
		}
	}

	public StringProperty loginProperty()
	{
		if (null == this.login)
		{
			this.login = new SimpleStringProperty(this._login);
		}
		return this.login;
	}

	public StringProperty firstnameProperty()
	{
		if (null == this.firstname)
		{
			this.firstname = new SimpleStringProperty(this._firstname);
		}
		return this.firstname;
	}

	public String getFirstname()
	{
		if (null == this.firstname)
		{
			return this._firstname;
		}
		else
		{
			return this.firstname.getValue();
		}
	}

	public void setFirstname(String firstname)
	{
		if (null == this.firstname)
		{
			this._firstname = firstname;
		}
		else
		{
			this.firstname.set(firstname);
		}
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

	@Override
	public String toString()
	{
		return this.getLogin() + ": " + this.getLastname() + ", " + this.getFirstname();
	}

}
