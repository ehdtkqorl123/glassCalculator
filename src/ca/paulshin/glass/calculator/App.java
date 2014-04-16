package ca.paulshin.glass.calculator;

import android.app.Application;

public class App extends Application {

	public final static boolean DEBUG = true;
	static App sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static App get() {
		return sInstance;
	}
}
