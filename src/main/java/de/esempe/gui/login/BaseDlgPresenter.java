package de.esempe.gui.login;

public abstract class BaseDlgPresenter<D extends BaseDialog>
{
	protected D dialog;

	protected BaseDlgPresenter()
	{
		this.dialog = null;
	}

	public void setView(final D dialog)
	{
		this.dialog = dialog;
		this.dialogInitialized();
	}

	protected abstract void dialogInitialized();
}
