package de.esempe.service.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectServiceTest
{
	private ProjectService objUnderTest;

	@BeforeEach
	public void setUp()
	{
		this.objUnderTest = new ProjectService();
	}

	@Test
	public void getProjects() throws IOException, InterruptedException
	{
		var result = this.objUnderTest.loadAll();
		assertThat(result).isNotNull();

	}
}
