package de.esempe.service.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest
{
	private UserService objUnderTest;

	@BeforeEach
	public void setUp()
	{
		this.objUnderTest = new UserService();
	}

	@Test
	public void getEntities() throws IOException, InterruptedException
	{
		final var result = this.objUnderTest.loadAll();
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
	}
}
