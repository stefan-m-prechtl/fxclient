package de.esempe.service.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.esempe.service.AbstractServiceTest;

/**
 * Integrationstest mit User-Service.
 *
 * @author Stefan Prechtl (www.esempe.de)
 *
 */
@Tag("integration-test")
@DisplayName("Integrationstest  UserService/REST-API Applikationsserver")
public class ProjectServiceTest extends AbstractServiceTest<ProjectService>
{

	@BeforeEach
	public void setUp()
	{
		super.setUp(ProjectService.class);
	}

	@Override
	protected ProjectService createObjUnderTest()
	{
		return new ProjectService(this.registry, this.logger);
	}

	@Test
	public void getEntities() throws IOException, InterruptedException
	{
		final var result = this.objUnderTest.loadAll();
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
	}

}
