package br.com.fiap.minichef.util;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class MiniChefApplication extends Application {
	
	private final String APPLICATION_ID = "vZRcNsJgJTUArSJx6lEOIHa336uIOdC2Bvv3N05J";
	private final String CLIENT_KEY = "fc2okWxf4ZFb8ck26sHLOKiadR36Q0i7V0lFp1ZQ";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
