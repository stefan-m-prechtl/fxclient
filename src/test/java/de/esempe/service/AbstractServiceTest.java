package de.esempe.service;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import de.esempe.ApplicationProperties;
import de.esempe.ApplicationRegistry;
import de.esempe.LoggerExposer;
import jakarta.inject.Inject;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(WeldJunit5Extension.class)
public abstract class AbstractServiceTest<S extends AbstractService>
{
	// konkrete Entitätsklasse - wird per Konstruktor gesetzt
	protected Class<S> serviceClass;
	protected S objUnderTest;

	@WeldSetup
	public WeldInitiator weld = WeldInitiator.of(WeldInitiator.createWeld().beanClasses(LoggerExposer.class, ApplicationRegistry.class));

	@Inject
	protected ApplicationRegistry registry;

	@Inject
	protected LoggerExposer logger;

	// BeforeAll
	@SuppressWarnings("PMD.JUnit4TestShouldUseBeforeAnnotation")
	protected void setUp(final Class<S> serviceClass)
	{
		this.serviceClass = serviceClass;
		// Anwendungs-Properties aus Datei lesen und intern speichern
		final var appProperties = new ApplicationProperties();
		appProperties.readProps();
		this.registry.putApplicationProperties(appProperties);

		this.objUnderTest = this.createObjUnderTest();

	}

	protected abstract S createObjUnderTest();

}