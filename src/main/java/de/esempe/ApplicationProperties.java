package de.esempe;

import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Klasse zur Verwaltung der ext. Konfigurationswerte aus der Datei 'appconfig.properties'.
 * 
 * @author etienne
 *
 */
@ApplicationScoped
public class ApplicationProperties
{
	public enum PropertyName
	{
		// TODO weitere Werte
		REST_URL("restURL"), USE_REST_API("useRestAPI"), DISPLAY_X("display_X"), DISPLAY_Y("display_Y");

		final String propertyKey;

		PropertyName(final String propertyKey)
		{
			this.propertyKey = propertyKey;
		}
	}

	private static final String PATH = "/appconfig.properties";
	private final Properties properties;

	public ApplicationProperties()
	{
		this.properties = new Properties();
	}

	public void readProps()
	{
		try
		{
			final var url = this.getClass().getResourceAsStream(PATH);
			this.properties.load(url);
		}
		catch (final Exception e)
		{
			// TODO echte Fehlerbehandlung
			e.printStackTrace();
		}
	}

	public String getStringProperty(final PropertyName key)
	{
		return this.properties.getProperty(key.propertyKey);
	}

	public boolean getBooleanProperty(final PropertyName key)
	{
		final var result = Boolean.valueOf(this.properties.getProperty(key.propertyKey));
		return result;
	}

	public int getIntegerProperty(final PropertyName key)
	{
		final var result = Integer.valueOf(this.properties.getProperty(key.propertyKey));
		return result;
	}

}
