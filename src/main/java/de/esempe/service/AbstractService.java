package de.esempe.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

import javax.json.bind.Jsonb;

import de.esempe.ApplicationProperties.PropertyName;
import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import jakarta.inject.Inject;

/**
 * Basisklasse für alle konkreten Services für den Zugriff vom Client per REST auf den Server.
 *
 * @author Stefan Prechtl
 *
 */
public class AbstractService
{
	protected String resourcePath;
	protected HttpClient client;
	protected Jsonb jsonb;

	@Inject
	protected ApplicationRegistry registry;

	@Inject
	protected LoggerExposer logger;

	protected AbstractService(final String resourcePath)
	{
		this.resourcePath = resourcePath;
		this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(3)).build();
	}

	protected HttpResponse<String> doGET(final String pathExtension)
	{
		try
		{
			//@formatter:off
			final var request = HttpRequest
					.newBuilder()
					.uri(URI.create(this.resourcePath + pathExtension))
					.header("Content-Type", "application/json")
					.GET()
					.build();
			//@formatter:on
			final var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
			this.registry.putCurrentHttpCode(response.statusCode());

			return response;

		}
		catch (final Exception e)
		{
			this.logger.fatalErrorConsumer().accept(e);
		}
		return null;
	}

	protected HttpResponse<String> doPOST(final String pathExtension, final String payload)
	{
		try
		{
			//@formatter:off
			final var request = HttpRequest
					.newBuilder()
					.uri(URI.create(this.resourcePath + pathExtension))
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.ofString(payload))
					.build();
			//@formatter:on
			final var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
			this.registry.putCurrentHttpCode(response.statusCode());

			return response;
		}
		catch (final Exception e)
		{
			this.logger.fatalErrorConsumer().accept(e);
		}
		return null;

	}

	// TODO: nach GUI-Prototypen-Phase (ohne Server) entfernen!
	protected boolean useRestAPI()
	{
		return this.registry.getApplicationProperties().getBooleanProperty(PropertyName.USE_REST_API);
	}

	// Für Unit-Tests
	public void init(final LoggerExposer logger, final ApplicationRegistry registry)
	{
		this.logger = logger;
		this.registry = registry;
	}
}
