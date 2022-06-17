package de.esempe;

import java.util.function.Consumer;

import jakarta.enterprise.inject.Produces;

/**
 * Klasse für die Ausgabe von Fehlern.
 * 
 * @author etienne
 *
 */
public class LoggerExposer
{
	@Produces
	public Consumer<Throwable> fatalErrorConsumer()
	{
		return LoggerExposer::printThrowable;
	}

	public static void printThrowable(final Throwable t)
	{
		System.err.println("Fataler Fehler: " + t.getMessage());
	}
}
