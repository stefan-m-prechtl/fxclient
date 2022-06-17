package de.esempe.gui;

/**
 * Basisklasse für alle konkreten Presenter .
 * 
 * @author Stefan M. Prechtl (www.esempe.de)
 *
 * @param <V> ugehöriger View (gemaess MVP-Pattern)
 */
public abstract class BasePresenter<V extends BaseView>
{
	protected V view;

	protected BasePresenter()
	{
		this.view = null;
	}

	public void setView(final V view)
	{
		this.view = view;
		this.viewInitialized();
	}

	protected abstract void viewInitialized();
}
