package de.esempe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import javax.json.bind.Jsonb;

public class AbstractService
{

	protected String resourcePath;
	protected HttpClient client;
	protected Jsonb jsonb;

	protected AbstractService(final String resourcePath)
	{
		this.resourcePath = resourcePath;

		this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(3)).build();
	}

	protected HttpResponse<String> doGET(final String pathExtension) throws IOException, InterruptedException
	{
		var request = HttpRequest.newBuilder().uri(URI.create(this.resourcePath + pathExtension)).header("Content-Type", "application/json").GET().build();

		var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

		return response;
	}

//	protected Client client = null;
//	protected WebTarget target = null;
//
//	protected String resourcePath;
//
//	protected Jsonb jsonb;
//
//	protected AbstractService(final String resourcePath)
//	{
//		this.resourcePath = resourcePath;
//		this.jsonb = JsonbBuilder.create(new JsonbConfig());
//	}
//
//	protected void initWebTarget()
//	{
//		this.client = ClientBuilder.newBuilder().connectTimeout(100, TimeUnit.MILLISECONDS).readTimeout(2, TimeUnit.MINUTES).build();
//		this.target = this.client.target(this.resourcePath);
//	}
//
//	protected Response doGET(final String pathExtension)
//	{
//		WebTarget target = this.target;
//		if (!pathExtension.isEmpty())
//		{
//			target = this.target.path(pathExtension);
//		}
//
//		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
//		final var res = invocationBuilder.get();
//		return res;
//	}
//
//	protected Response doPOST(final String pathExtension, final String payload)
//	{
//		WebTarget target = this.target;
//		if (!pathExtension.isEmpty())
//		{
//			target = this.target.path(pathExtension);
//		}
//
//		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
//		final var res = invocationBuilder.post(Entity.json(payload));
//
//		return res;
//
//	}

}
