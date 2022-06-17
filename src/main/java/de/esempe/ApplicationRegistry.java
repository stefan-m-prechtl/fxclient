package de.esempe;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationRegistry
{
	private final Map<String, Object> objMap = new HashMap<>();

	public void putJsonWebToken(final String token)
	{
		this.objMap.put("jwt", token);
	}

	public String getJsonWebToken()
	{
		return (String) this.objMap.get("jwt");
	}

	public ApplicationProperties getApplicationProperties()
	{
		return (ApplicationProperties) this.objMap.get("props");
	}

	public void putApplicationProperties(final ApplicationProperties props)
	{
		this.objMap.put("props", props);
	}

	public void putLocale(final Locale locale)
	{
		this.objMap.put("locale", locale);
	}

	public Locale getLocale()
	{
		return (Locale) this.objMap.get("locale");
	}

	public void putUsername(final String username)
	{
		this.objMap.put("username", username);
	}

	public String getUsername()
	{
		return (String) this.objMap.get("username");
	}

	public void putCurrentErrorMsg(final String errmsg)
	{
		this.objMap.put("currenterrmsg", errmsg);
	}

	public String getCurrentErrorMsg()
	{
		return (String) this.objMap.get("currenterrmsg");
	}

	public void putCurrentHttpCode(final int httpCode)
	{
		this.objMap.put("currenthttpcode", httpCode);
	}

	public int getCurrentHttpCode()
	{
		return (Integer) this.objMap.get("currenthttpcode");
	}
}
