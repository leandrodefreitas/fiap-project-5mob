package br.com.fiap.minichef.install;

import android.content.Context;
import java.io.File;
import java.net.URL;

abstract class Updater 
{
	protected static final String SERVICE_RELEASE = "FIAP.apk";
	protected Context context;
	public String PATH;
	protected URL updateUrl;

	public Updater( Context context ) 
	{
		PATH = context.getFilesDir().getAbsolutePath() + File.separator + SERVICE_RELEASE;
		this.context = context;
	}

	public abstract boolean hasUpdate();

	public abstract void update();
}
