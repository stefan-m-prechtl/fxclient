package de.esempe.model.project;

import java.io.Serializable;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final SimpleStringProperty propName = new SimpleStringProperty();

	private final UUID objId;

	private String description;

	public Project(final UUID objId, final String name)
	{
		this.objId = objId;
		this.setName(name);
	}

	public UUID getObjId()
	{
		return this.objId;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public void setName(final String value)
	{
		this.propName.set(value);
	}

	public String getName()
	{
		return this.propName.get();
	}

	public StringProperty nameProperty()
	{
		return this.propName;
	}

	@Override
	public String toString()
	{
		return this.getName();
	}

}
