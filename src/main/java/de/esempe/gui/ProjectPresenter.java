package de.esempe.gui;

import de.esempe.gui.project.ProjectView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProjectPresenter extends BasePresenter<ProjectView>
{
	@Inject
	protected ProjectPresenter(final ProjectView view)
	{
		super(view);
	}

	@PostConstruct
	void created()
	{

	}

	void initView(@ObservesAsync final MessageEvent e)
	{
		// System.out.println("got event:" + e.getMsg());
	}

}
