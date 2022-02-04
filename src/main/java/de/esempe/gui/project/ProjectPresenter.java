package de.esempe.gui.project;

import java.util.List;

import de.esempe.gui.BasePresenter;
import de.esempe.model.project.Project;
import de.esempe.service.project.ProjectService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@ApplicationScoped
public class ProjectPresenter extends BasePresenter<ProjectView>
{
	// Model
	List<Project> projectList;

	// Schnittstelle zur REST-Service
	@Inject
	ProjectService service;

	@Inject
	protected ProjectPresenter(final ProjectView view)
	{
		super(view);
	}

	// Alle Aktionen nach der vollständigen Initialisierung des Views
	void viewInitialized(@ObservesAsync ProjectView view)
	{
		// Alle Projekte per REST-API laden
		this.projectList = this.service.loadAll();
		// Modell zu View-Modell umwandeln
		final ObservableList<String> searchresult = FXCollections.observableArrayList();
		for (final Project project : this.projectList)
		{
			searchresult.add(project.getName());
		}
		// Projekte im View anzeigen
		this.view.showList(searchresult);

	}

}
